package cn.edu.sustc.androidclient.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.common.rx.AppSchedulerProvider;
import cn.edu.sustc.androidclient.model.MyDataBase;
import dagger.Module;
import dagger.Provides;

import static cn.edu.sustc.androidclient.common.Constants.DATABASE_NAME;

/**
 * Application wide modules
 */
@Module(includes = {
        NetworkModule.class,
        ServiceModule.class
})
public class AppModule {
    /**
     * provide application context
     *
     * @param application MyApplication
     * @return Context
     */
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    /**
     * provide scheduler
     *
     * @return AppSchedulerProvider
     */
    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    /**
     * provide SharePreferences
     *
     * @return SharedPreferences
     */
    @Provides
    @Singleton
    SharedPreferences provideSharePreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    /**
     * provide room database
     *
     * @param appContext Application Context
     * @return MyDataBase
     */
    @Provides
    @Singleton
    MyDataBase provideDataBase(Context appContext) {
        // don't allow main thread query
        // .allowMainThreadQueries()
        return Room
                .databaseBuilder(appContext, MyDataBase.class, DATABASE_NAME)
                .build();
    }
}
