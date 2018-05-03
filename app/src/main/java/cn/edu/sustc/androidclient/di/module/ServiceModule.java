package cn.edu.sustc.androidclient.di.module;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.model.service.FileService;
import cn.edu.sustc.androidclient.model.service.TaskService;
import cn.edu.sustc.androidclient.model.service.UserService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServiceModule {
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
