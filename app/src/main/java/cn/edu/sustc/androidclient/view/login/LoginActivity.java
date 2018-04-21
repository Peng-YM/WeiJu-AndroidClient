package cn.edu.sustc.androidclient.view.login;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityLoginBinding;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.view.main.MainActivity;
import cn.edu.sustc.androidclient.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {
    private AlertDialog alertDialog;
    private LoginViewModel model;
    private ActivityLoginBinding binding;

    // data binding
    public ObservableField<String> email;
    public ObservableField<String> password;

    public static void start(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<LoginViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, LoginViewModel viewModel, ActivityLoginBinding binding) {
        this.model = viewModel;
        this.binding = binding;

        binding.setLoginActivity(this);
        initData();
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void initData(){
        email = new ObservableField<>();
        password = new ObservableField<>();
    }

    private void initViews(){
        // set alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setCancelable(false)
                .setPositiveButton("OK", (dialogInterface, i) -> {});
        alertDialog = builder.create();
        binding.loginProgressBar.setVisibility(View.GONE);
    }

    public void login(View view){
        model.login(new Session(email.get(), password.get()));
        model.getCredential().observe(this, resource -> {
            if (resource != null){
                switch (resource.status){
                    case ERROR:
                        String errorInfo = resource.message;
                        alertDialog.setMessage(errorInfo);
                        alertDialog.show();
                        binding.loginProgressBar.setVisibility(View.GONE);
                        break;
                    case LOADING:
                        binding.loginProgressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        // save credential and go to main activity
                        saveCredential(resource.data);
                        MainActivity.start(this);
                        binding.loginProgressBar.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void goToRegistration(View view){
        Logger.d("Go to registration");
        RegistrationActivity.start(this);
    }

    private void saveCredential(Credential credential){
        SharedPreferences preferences = SharePreferenceHelper.getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", credential.id);
        editor.putString("token", credential.token);
        editor.apply();
    }
}

