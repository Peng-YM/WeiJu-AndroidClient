package cn.edu.sustc.androidclient.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import cn.edu.sustc.androidclient.di.ViewModelKey;
import cn.edu.sustc.androidclient.view.login.LoginViewModel;
import cn.edu.sustc.androidclient.view.main.MainViewModel;
import cn.edu.sustc.androidclient.view.task.TaskViewModel;
import cn.edu.sustc.androidclient.di.ViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


/**
 * abstract class to inject ViewModels
 * */
@Module
public abstract class ViewModelModule {
    /**
     * provide ViewModelFactory
     * */
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
}
