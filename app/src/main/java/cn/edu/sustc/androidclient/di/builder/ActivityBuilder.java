package cn.edu.sustc.androidclient.di.builder;

import cn.edu.sustc.androidclient.view.authentication.AuthenticationModule;
import cn.edu.sustc.androidclient.view.authentication.LoginActivity;
import cn.edu.sustc.androidclient.view.authentication.RegistrationActivity;
import cn.edu.sustc.androidclient.view.main.MainActivity;
import cn.edu.sustc.androidclient.view.main.MainModule;
import cn.edu.sustc.androidclient.view.profile.ProfileActivity;
import cn.edu.sustc.androidclient.view.task.TaskModule;
import cn.edu.sustc.androidclient.view.task.annotationtask.AnnotationTaskActivity;
import cn.edu.sustc.androidclient.view.task.annotationtask.TagEditorActivity;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskActivity;
import cn.edu.sustc.androidclient.view.task.publishtask.TaskPublishActivity;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailActivity;
import cn.edu.sustc.androidclient.view.task.tasklist.TaskFragmentProvider;
import cn.edu.sustc.androidclient.view.task.taskmanager.TaskManagerActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * abstract class to inject activities
 * whenever you add an activity, you need to add it's dependencies here.
 */
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(
            modules = {AuthenticationModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(
            modules = {AuthenticationModule.class})
    abstract RegistrationActivity bindRegistrationActivity();

    @ContributesAndroidInjector(
            modules = {MainModule.class, TaskFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(
            modules = {TaskModule.class})
    abstract TaskDetailActivity bindTaskDetailActivity();

    @ContributesAndroidInjector(
            modules = {TaskModule.class})
    abstract TagEditorActivity bindTagEditorActivity();

    @ContributesAndroidInjector(
            modules = {TaskModule.class})
    abstract AnnotationTaskActivity bindAnnotationTaskActivity();

    @ContributesAndroidInjector(
            modules = {TaskModule.class})
    abstract CollectionTaskActivity bindCollectionTaskActivity();

    @ContributesAndroidInjector(
            modules = {TaskModule.class})
    abstract TaskPublishActivity bindTaskPublishActivity();

    @ContributesAndroidInjector(
            modules = MainModule.class)
    abstract ProfileActivity bindProfileActivity();

    @ContributesAndroidInjector(
            modules = {TaskModule.class, TaskFragmentProvider.class}
    )
    abstract TaskManagerActivity bindTaskManagerActivity();
}
