package cn.edu.sustc.androidclient.di.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.MyApplication;
import cn.edu.sustc.androidclient.common.Constants;
import cn.edu.sustc.androidclient.common.http.NetworkConnectionInterceptor;
import cn.edu.sustc.androidclient.common.http.NetworkStateEvent;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
     * @param connectivityManager Injected ConnectivityManager to provide network info
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ConnectivityManager connectivityManager) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        // add network state interceptor
        okHttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnected();
                if (isConnected) {
                    EventBus.getDefault().post(new NetworkStateEvent(true));
                }
                return isConnected;
            }

            @Override
            public void onInternetUnavailable() {
                // broadcast this event to activity/fragment/service through EventBus
                Logger.d("Network Unavailable!");
                EventBus.getDefault().post(new NetworkStateEvent(false));
            }
        });
        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectionManager(MyApplication application) {
        Context context = application.getApplicationContext();
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
