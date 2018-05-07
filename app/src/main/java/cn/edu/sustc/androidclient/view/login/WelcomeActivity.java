package cn.edu.sustc.androidclient.view.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.sustc.androidclient.R;

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.button_login)
    Button login;
    @BindView(R.id.button_registration)
    Button registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        login.setOnClickListener(v -> {
            LoginActivity.start(this);
        });

        registration.setOnClickListener(v -> {
            RegistrationActivity.start(this);
        });
    }
}
