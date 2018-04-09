package cn.edu.sustc.androidclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.edu.sustc.androidclient.common.BaseActivity;
import cn.edu.sustc.androidclient.common.LogCatStrategy;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.ui.login.LoginActivity;

public class Application extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // global SharedPreferences
        Context applicationContext = getApplicationContext();
        SharedPreferences globalPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharePreferenceHelper.setPreference(globalPrefs);

        // Logger
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .logStrategy(new LogCatStrategy())
                .methodCount(1)
                .build();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        Logger.d("Application Started");

        // show login activity
        LoginActivity.start(Application.this);
    }
}
