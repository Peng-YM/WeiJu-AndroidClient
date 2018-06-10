package cn.edu.sustc.androidclient.view.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.AndroidSupportInjection;

/**
 * MVVM fragment
 *
 * @param <M> Class type of ViewModel
 * @param <B> Class type of ViewDataBinding
 */
public abstract class BaseFragment<M extends ViewModel, B extends ViewDataBinding> extends Fragment {
    private BaseActivity activity;
    private B binding;
    private View rootView;

    protected abstract M getViewModel();

    protected abstract @LayoutRes
    int getLayoutId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        activity.onFragmentDetached();
        activity = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        rootView = binding.getRoot();
        return rootView;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public B getBinding() {
        return binding;
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    public void hideKeyBoard() {
        if (activity != null) {
            activity.hideKeyBoard();
        }
    }

    public boolean isNetworkConnected() {
        return activity != null && activity.isNetworkConnected();
    }

    public interface CallBack {
        void onFragmentAttached();

        void onFragmentDetached();
    }
}
