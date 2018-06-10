package cn.edu.sustc.androidclient.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityRegistrationBinding;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class RegistrationActivity extends BaseActivity<LoginViewModel, ActivityRegistrationBinding> {
    // data binding
    public ObservableField<String> email;
    public ObservableField<String> password;
    @Inject
    LoginViewModel model;
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
        Session session = new Session(email.get(), password.get());
        model.registration(session).observe(this, resource -> {
            showLoading();
            if (resource != null) {
                switch (resource.status) {
                    case ERROR:
                        hideLoading();
                        showAlertDialog(getString(R.string.alert), resource.message);
                        break;
                    case LOADING:
                        break;
                    case SUCCESS:
                        LoginActivity.start(this);
                        hideLoading();
                        break;
                }
            }
        });
    }
}
