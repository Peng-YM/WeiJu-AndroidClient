package cn.edu.sustc.androidclient.common;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static volatile Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:3000/api/v1/";
    private RetrofitFactory(){}

    // double checked lock singleton
    public static Retrofit getInstance(){
        if(retrofit == null){
            synchronized (RetrofitFactory.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
