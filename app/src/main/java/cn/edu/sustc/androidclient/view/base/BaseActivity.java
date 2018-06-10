package cn.edu.sustc.androidclient.view.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import cn.edu.sustc.androidclient.common.ActivityCollector;
import cn.edu.sustc.androidclient.common.utils.CommonUtils;
import cn.edu.sustc.androidclient.common.utils.NetworkUtils;
import dagger.android.AndroidInjection;

/**
 * Base class for all activities:
 * provides Model-View-ViewModel injection.
 */
public abstract class BaseActivity<M extends ViewModel, B extends ViewDataBinding>
        extends AppCompatActivity implements BaseFragment.CallBack{
    private B binding;
    private ProgressDialog progressDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
    }

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

    @Override
    public void onFragmentAttached(){ }

    @Override
    public void onFragmentDetached(){ }

    public B getBinding(){
        return binding;
    }

    protected abstract @LayoutRes
    int getLayoutResId();


    public void showAlertDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .create();
        dialog.show();
    }

    public void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null){
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }

    public void showLoading(){
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    public boolean isNetworkConnected(){
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    private void performDependencyInjection(){
        AndroidInjection.inject(this);
    }
}
