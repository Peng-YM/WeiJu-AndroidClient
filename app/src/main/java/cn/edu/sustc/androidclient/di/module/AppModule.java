package cn.edu.sustc.androidclient.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.MyApplication;
import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.common.utils.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.MyDataBase;
import dagger.Module;
import dagger.Provides;

import static cn.edu.sustc.androidclient.common.Constants.DATABASE_NAME;

@Module(includes = {
        ViewModelModule.class,
        NetworkModule.class,
        ServiceModule.class
})
public class AppModule {
    /**
     * provide application context
     * @param application MyApplication
     * @return Context
     */
    @Provides
    @Singleton
    Context provideContext(MyApplication application) {
        return application.getApplicationContext();
    }

    /**
     * provide scheduler
     * @return AppSchedulerProvider
     */
    @Provides
    @Singleton
    AppSchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    /**
     * provide SharePreferences
     * @return SharedPreferences
     */
    @Provides
    @Singleton
    SharedPreferences provideSharePreferences() {
        return SharePreferenceHelper.getPreferences();
    }

    /**
     * provide room database
     * @param appContext Application Context
     * @return MyDataBase
     */
    @Provides
    @Singleton
    MyDataBase provideDataBase(Context appContext){
        // don't allow main thread query
        // .allowMainThreadQueries()
        MyDataBase dataBase = Room.databaseBuilder(appContext, MyDataBase.class, DATABASE_NAME)
                .build();
        return dataBase;
    }
}
