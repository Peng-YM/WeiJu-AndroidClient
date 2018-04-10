package cn.edu.sustc.androidclient.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityLoginBinding;
public class LoginActivity extends BaseActivity {

    private AlertDialog alertDialog;
    public String test;

    public static void start(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.test = "Hello";
        setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel model =  new LoginViewModel(LoginActivity.this);
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
        model.setAlertDialog(alertDialog);
        binding.setLoginViewModel(model);
    }

}

