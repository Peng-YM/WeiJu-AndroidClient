package cn.edu.sustc.androidclient.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.common.Constants;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Modules that provide network functionality
 */
@Module
public class NetworkModule {
    /**
     * Retrofit provider
     *
     * @param client OkHttpClient instance injected by Dagger
     * @return Retrofit
     */
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * OkHttpClient Provider
     *
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(5, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(5, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging
        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        return okHttpClientBuilder.build();
    }
}
