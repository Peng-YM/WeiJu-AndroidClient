package cn.edu.sustc.androidclient.common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://your.api.url/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
