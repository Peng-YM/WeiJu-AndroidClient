package cn.edu.sustc.androidclient.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityLoginBinding;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.view.base.BaseActivity;
import cn.edu.sustc.androidclient.view.main.MainActivity;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {
    // data binding
    public ObservableField<String> email;
    public ObservableField<String> password;
    @Inject
    LoginViewModel model;
    @Inject
    SharedPreferences preferences;
    @Inject
    AwesomeValidation awesomeValidation;

    private ActivityLoginBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle instance) {
        super.onCreate(instance);
        binding = getBinding();
        binding.setLoginActivity(this);
        initData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void initData() {
        email = new ObservableField<>();
        password = new ObservableField<>();
        // init validations
        awesomeValidation.addValidation(binding.loginEmail, Patterns.EMAIL_ADDRESS,
                getString(R.string.email_error));
        awesomeValidation.addValidation(binding.loginPassword, s -> s.trim().length() != 0,
                getString(R.string.alert_field_empty));
    }

    public void login(View view) {
        if (awesomeValidation.validate()) {
            model.login(new Session(email.get(), password.get())).observe(this, resource -> {
                showLoading();
                if (resource != null) {
                    switch (resource.status) {
                        case SUCCESS:
                            // save credential and go to main activity
                            saveCredential(resource.data);
                            MainActivity.start(this);
                            hideLoading();
                            finish();
                            break;
                        case ERROR:
                            String errorInfo = resource.message;
                            showAlertDialog(getString(R.string.error), errorInfo);
                            hideLoading();
                            break;
                        case LOADING:
                            break;
                    }
                }
            });
        }
    }

    private void saveCredential(Credential credential) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", credential.userId);
        editor.putString("token", credential.token);
        editor.apply();
    }
}

