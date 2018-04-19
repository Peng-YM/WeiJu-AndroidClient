package cn.edu.sustc.androidclient.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.MyApplication;
import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ViewModelModule.class,
        NetworkModule.class
})
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(MyApplication application){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider(){
        return new AppSchedulerProvider();
    }
}
