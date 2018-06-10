package cn.edu.sustc.androidclient.view.base;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.ActivityCollector;
import cn.edu.sustc.androidclient.common.http.NetworkStateEvent;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Base class for all activities:
 * provides Model-View-ViewModel injection.
 */
public abstract class BaseActivity<M extends ViewModel, B extends ViewDataBinding> extends AppCompatActivity {
    private B binding;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
    }

    public B getBinding(){
        return binding;
    }

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
    protected void showAlertDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .create();
        dialog.show();
    }

    private void performDependencyInjection(){
        AndroidInjection.inject(this);
    }
}
