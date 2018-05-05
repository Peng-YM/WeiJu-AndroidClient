package cn.edu.sustc.androidclient.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.view.login.LoginActivity;
import cn.edu.sustc.androidclient.view.login.WelcomeActivity;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);

    }
}
