package cn.edu.sustc.androidclient.model;

import cn.edu.sustc.androidclient.common.Status;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * a generic class that describes a data with a status
 * */
public class MyResource<T> {
    @NonNull public final Status status;
    @Nullable public final T data;
    @Nullable public final String message;

    private MyResource(@NonNull Status status, @Nullable T data, @Nullable String message){
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MyResource<T> success(@NonNull T data){
        return new MyResource<>(Status.SUCCESS, data, null);
    }

    public static <T> MyResource<T> error(String msg, @Nullable T data){
        return new MyResource<>(Status.ERROR, data, msg);
    }

    public static <T> MyResource<T> loading(@Nullable T data){
        return new MyResource<>(Status.LOADING, data, null);
    }
}
