package cn.edu.sustc.androidclient.view.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityRegistrationBinding;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;

public class RegistrationActivity extends BaseActivity<LoginViewModel, ActivityRegistrationBinding> {
    // data binding
    public ObservableField<String> email;
    public ObservableField<String> password;
    private AlertDialog alertDialog;
    private LoginViewModel model;
    private ActivityRegistrationBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<LoginViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LoginViewModel viewModel, ActivityRegistrationBinding binding) {
        this.model = viewModel;
        this.binding = binding;

        binding.setRegistrationActivity(this);
        binding.registrationProgressBar.setVisibility(View.GONE);
        initData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_registration;
    }

    private void initData() {
        email = new ObservableField<>();
        password = new ObservableField<>();
    }

    public void registration(View view) {
        Logger.d("User %s is attempt to registration...", email.get());
        Session session = new Session(email.get(), password.get());
        model.registration(session).observe(this, data -> {
            if (data != null) {
                switch (data.status) {
                    case LOADING:
                        binding.registrationProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        Logger.d("Registration Successfully");
                        binding.registrationProgressBar.setVisibility(View.GONE);
                        showAlertDialog("", getString(R.string.register_success));
                        LoginActivity.start(this);
                        finish();
                        break;
                    case ERROR:
                        showAlertDialog(getString(R.string.alert), data.message);
                        binding.registrationProgressBar.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }
}
