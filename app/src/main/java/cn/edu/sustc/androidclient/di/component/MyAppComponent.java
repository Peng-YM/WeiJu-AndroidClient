package cn.edu.sustc.androidclient.di.component;

import android.app.Application;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.MyApplication;
import cn.edu.sustc.androidclient.di.builder.ActivityBuilder;
import cn.edu.sustc.androidclient.di.module.AppModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Dagger application component to inject application
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface MyAppComponent {
    void inject(MyApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        MyAppComponent build();
    }
}
