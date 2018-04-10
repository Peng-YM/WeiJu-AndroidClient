package cn.edu.sustc.androidclient;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cn.edu.sustc.androidclient.common.LogCatStrategy;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.ui.login.LoginActivity;

// application class which is responsible to initialize global variables
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // global SharedPreferences
        Context applicationContext = getApplicationContext();
        SharedPreferences globalPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharePreferenceHelper.setPreference(globalPrefs);

        // Initialize Logger
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
        LoginActivity.start(MyApplication.this);
    }
}
