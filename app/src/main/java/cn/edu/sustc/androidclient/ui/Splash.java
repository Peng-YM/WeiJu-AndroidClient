package cn.edu.sustc.androidclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.BaseActivity;
import cn.edu.sustc.androidclient.ui.login.LoginActivity;

public class Splash extends BaseActivity {
    private static int SPLASH_TIMEOUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( Splash.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);

    }
}
