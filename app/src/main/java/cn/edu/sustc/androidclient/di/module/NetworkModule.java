package cn.edu.sustc.androidclient.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.common.Constants;
import cn.edu.sustc.androidclient.model.service.FileService;
import cn.edu.sustc.androidclient.model.service.TaskService;
import cn.edu.sustc.androidclient.model.service.UserService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Provides
    @Singleton
    TaskService provideTaskService(Retrofit retrofit) {
        return retrofit.create(TaskService.class);
    }

    @Provides
    @Singleton
    FileService provideFileService(Retrofit retrofit) {
        return retrofit.create(FileService.class);
    }
}
