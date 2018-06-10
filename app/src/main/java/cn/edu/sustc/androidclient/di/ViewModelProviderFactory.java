package cn.edu.sustc.androidclient.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {
    private V viewModel;

    public ViewModelProviderFactory(V viewModel){
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())){
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
