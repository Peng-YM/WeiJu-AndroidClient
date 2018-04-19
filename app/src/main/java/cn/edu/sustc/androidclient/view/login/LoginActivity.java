package cn.edu.sustc.androidclient.view.login;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityLoginBinding;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.view.main.MainActivity;
import cn.edu.sustc.androidclient.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {
    private AlertDialog alertDialog;
    private LoginViewModel model;

    // data binding
    public ObservableField<Integer> progressBarVisibility;
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
        binding.setLoginActivity(this);
        initData();
        initViews();
        initListeners();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void initData(){
        progressBarVisibility = new ObservableField<>();
        email = new ObservableField<>();
        password = new ObservableField<>();

        // set progress bar visibility
        progressBarVisibility.set(View.GONE);
    }

    private void initViews(){
        // set alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        alertDialog = builder.create();
    }

    private void initListeners(){
        model.getStatus().observe(this, status -> {
            switch (status){
                case LOGIN_SUCCESS:
                    Logger.d("Go to MainActivity");
                    // go to main activity
                    MainActivity.start(this);
                    break;
                case LOGIN_FAILED:
                    // stop progress bar
                    progressBarVisibility.set(View.GONE);
                    // show alert dialog
                    alertDialog.setMessage(getResources().getString(R.string.alert_bad_credential));
                    alertDialog.show();
                    break;
                case PROGRESSING:
                    // show progress bar
                    progressBarVisibility.set(View.VISIBLE);
                    break;
                default:
                    break;
            }
        });
    }

    public void login(View view){
        model.login(new Session(email.get(), password.get()));
    }

    public void goToRegistration(View view){
        RegistrationActivity.start(this);
    }
}
