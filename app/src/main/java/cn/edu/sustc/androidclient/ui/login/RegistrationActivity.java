package cn.edu.sustc.androidclient.ui.login;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends BaseActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActivityRegistrationBinding registrationBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_registration);
        LoginViewModel model =  new LoginViewModel(RegistrationActivity.this);
        // set alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        model.setAlertDialog(alertDialog);
        registrationBinding.setLoginViewModel(model);
    }
}
