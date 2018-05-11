package cn.edu.sustc.androidclient.common.base;

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
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.ActivityCollector;
import cn.edu.sustc.androidclient.common.http.NetworkStateEvent;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Base class for all activities:
 * provides Model-View-ViewModel injection.
 */
public abstract class BaseActivity<M extends ViewModel, B extends ViewDataBinding> extends DaggerAppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    ConnectivityManager connectivityManager;

    NetworkStateEvent networkStateEvent;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutResId());
        ViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        onCreate(savedInstanceState, (M) viewModel, (B) binding);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        networkStateEvent = new NetworkStateEvent(isConnected);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangedEvent(NetworkStateEvent event) {
        Toast.makeText(this, "Network Connected State: " + event.isConnected(), Toast.LENGTH_SHORT).show();
        networkStateEvent = event;
    }

    protected boolean isNetworkConnected() {
        return networkStateEvent.isConnected();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    protected void showAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(getString(R.string.alert))
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .create();
        dialog.show();
    }
}
