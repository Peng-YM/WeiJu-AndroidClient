package cn.edu.sustc.androidclient.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import cn.edu.sustc.androidclient.di.ViewModelFactory;
import cn.edu.sustc.androidclient.di.ViewModelKey;
import cn.edu.sustc.androidclient.view.login.LoginViewModel;
import cn.edu.sustc.androidclient.view.main.MainViewModel;
import cn.edu.sustc.androidclient.view.task.TaskViewModel;
import cn.edu.sustc.androidclient.view.task.annotationtask.AnnotationTaskViewModel;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskViewModel;
import cn.edu.sustc.androidclient.view.task.publishtask.TaskPublishViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


/**
 * abstract class to inject ViewModels
 */
@Module
public abstract class ViewModelModule {
    /**
     * provide ViewModelFactory
     */
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel.class)
    abstract ViewModel bindTaskViewModel(TaskViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CollectionTaskViewModel.class)
    abstract ViewModel bindCollectionTaskViewModel(CollectionTaskViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AnnotationTaskViewModel.class)
    abstract ViewModel bindAnnotationTaskVM(AnnotationTaskViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TaskPublishViewModel.class)
    abstract ViewModel bindTaskPublishViewModel(TaskPublishViewModel viewModel);
}
