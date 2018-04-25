package cn.edu.sustc.androidclient.common.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.ActivityCollector;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Base class for all activities:
 * provides Model-View-ViewModel injection.
 */
public abstract class BaseActivity<M extends ViewModel, B extends ViewDataBinding> extends DaggerAppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutResId());
        ViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        onCreate(savedInstanceState, (M) viewModel, (B) binding);
    }

    protected abstract Class<M> getViewModel();

    protected abstract void onCreate(Bundle instance, M viewModel, B binding);

    protected abstract @LayoutRes
    int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }
}
