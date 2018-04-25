package cn.edu.sustc.androidclient.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.MyApplication;
import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.common.utils.SharePreferenceHelper;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        ViewModelModule.class,
        NetworkModule.class
})
public class AppModule {
    // provide application context
    @Provides
    @Singleton
    Context provideContext(MyApplication application) {
        return application.getApplicationContext();
    }

    // provide scheduler
    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    // provide SharePreferences
    @Provides
    @Singleton
    SharedPreferences provideSharePreferences() {
        return SharePreferenceHelper.getPreferences();
    }
}
