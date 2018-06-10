package cn.edu.sustc.androidclient.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityRegistrationBinding;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class RegistrationActivity extends BaseActivity<LoginViewModel, ActivityRegistrationBinding> {
    @Inject
    LoginViewModel model;
    // data binding
    public ObservableField<String> email;
    public ObservableField<String> password;

    private AlertDialog alertDialog;
    private ActivityRegistrationBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        binding.setRegistrationActivity(this);
        initData();
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_registration;
    }

    private void initData() {
        email = new ObservableField<>();
        password = new ObservableField<>();
    }

    private void initViews() {
        // set alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setCancelable(false)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                });
        alertDialog = builder.create();
        binding.registrationProgressBar.setVisibility(View.GONE);
    }

    public void registration(View view) {
        User newUser = new User();
        newUser.password = password.get();
        newUser.email = email.get();
        model.registration(newUser);
        model.getCredential().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        String errorInfo = resource.message;
                        alertDialog.setMessage(errorInfo);
                        alertDialog.show();
                        binding.registrationProgressBar.setVisibility(View.GONE);
                        break;
                    case LOADING:
                        binding.registrationProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        // save credential and go to login
                        LoginActivity.start(this);
                        binding.registrationProgressBar.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
