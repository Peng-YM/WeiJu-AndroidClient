package cn.edu.sustc.androidclient.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import cn.edu.sustc.androidclient.di.ViewModelKey;
import cn.edu.sustc.androidclient.viewmodel.LoginViewModel;
import cn.edu.sustc.androidclient.viewmodel.ViewModelFactory;
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
}
