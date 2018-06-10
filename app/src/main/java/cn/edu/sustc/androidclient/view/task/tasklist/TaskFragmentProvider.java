package cn.edu.sustc.androidclient.view.task.tasklist;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TaskFragmentProvider {
    @ContributesAndroidInjector(modules = TaskFragmentModule.class)
    abstract TaskFragment provideTaskFragment();
}
