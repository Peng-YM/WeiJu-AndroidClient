package cn.edu.sustc.androidclient.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.BaseActivity;

public class RegistrationActivity extends BaseActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
}
