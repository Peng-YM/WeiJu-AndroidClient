package cn.edu.sustc.androidclient.di;

import cn.edu.sustc.androidclient.view.login.LoginActivity;
import cn.edu.sustc.androidclient.view.login.RegistrationActivity;
import cn.edu.sustc.androidclient.view.main.MainActivity;
import cn.edu.sustc.androidclient.view.profile.UserProfileActivity;
import cn.edu.sustc.androidclient.view.task.annotationtask.AnnotationTaskActivity;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskActivity;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailActivity;
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

    @ContributesAndroidInjector
    abstract UserProfileActivity bindUserProfileActivity();

    @ContributesAndroidInjector
    abstract CollectionTaskActivity bindCollectionTaskActivity();

    @ContributesAndroidInjector
    abstract AnnotationTaskActivity bindAnnotationTaskActivity();

}
