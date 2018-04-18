package cn.edu.sustc.androidclient.common;

import javax.inject.Singleton;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RetrofitFactory {
    private static volatile Retrofit retrofit = null;
//    private static final String BASE_URL = "http://10.0.2.2:3000/";
    private static final String BASE_URL = "http://69.171.71.251:3000/";
    private RetrofitFactory(){}

    // double checked lock singleton
    public static Retrofit getInstance(){
        if(retrofit == null){
            synchronized (RetrofitFactory.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(
                                    RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())
                            )
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
