package cn.edu.sustc.androidclient.di;

import javax.inject.Singleton;

import cn.edu.sustc.androidclient.MyApplication;
import cn.edu.sustc.androidclient.di.module.AppModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
})
public interface MyAppComponent extends AndroidInjector<MyApplication>{

    // bind AppComponent to MyApplication
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication>{}
}
