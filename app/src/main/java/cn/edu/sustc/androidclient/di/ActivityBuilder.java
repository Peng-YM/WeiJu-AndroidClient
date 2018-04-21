package cn.edu.sustc.androidclient.di;

import cn.edu.sustc.androidclient.view.login.LoginActivity;
import cn.edu.sustc.androidclient.view.login.RegistrationActivity;
import cn.edu.sustc.androidclient.view.main.MainActivity;
import cn.edu.sustc.androidclient.view.task.TaskDetailActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 *  abstract class to inject activities
 * */
@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract RegistrationActivity bindRegistrationActivity();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract TaskDetailActivity bindTaskDetailActivity();

}
