package cn.edu.sustc.androidclient.di;

import cn.edu.sustc.androidclient.view.login.LoginActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 *  abstract class to inject activities
 * */
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();
}
