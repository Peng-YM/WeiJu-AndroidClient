package cn.edu.sustc.androidclient.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.View;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityRegistrationBinding;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class RegistrationActivity extends BaseActivity<LoginViewModel, ActivityRegistrationBinding> {
    // data binding
    public ObservableField<String> email;
    public ObservableField<String> password;
    // injected modules
    @Inject
    LoginViewModel model;
    @Inject
    AwesomeValidation validation;

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
        validation.addValidation(binding.signupEmail,
                Patterns.EMAIL_ADDRESS, getString(R.string.email_error));
        validation.addValidation(binding.signupPassword,
                s -> s.trim().length() != 0, getString(R.string.alert_field_empty));
    }

    public void registration(View view) {
        if (validation.validate()) {
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
}
