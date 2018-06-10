package cn.edu.sustc.androidclient;

import android.app.Activity;
import android.app.Application;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.utils.SharePreferenceHelper;
import cn.edu.sustc.androidclient.di.component.DaggerMyAppComponent;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Application class which is responsible to initialize global variables
 */
public class MyApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector(){
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // dependency injection
        DaggerMyAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        setupSharedPreferences();
        setupLogger();
        Logger.d("Application Started");
    }

    private void setupSharedPreferences(){
        // Global SharedPreferences
        Context applicationContext = getApplicationContext();
        SharedPreferences globalPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharePreferenceHelper.setPreference(globalPrefs);
    }

    private void setupLogger(){
        // Initialize Logger
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .logStrategy(new LogCatStrategy())
                .methodCount(1)
                .build();
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private class LogCatStrategy implements LogStrategy {


        private Handler handler;
        private long lastTime = SystemClock.uptimeMillis();
        private long offset = 5;

        LogCatStrategy() {
            HandlerThread thread = new HandlerThread("thread_print");
            thread.start();
            handler = new Handler(thread.getLooper());
        }

        @Override
        public void log(final int priority, final String tag, @NonNull final String message) {
            lastTime += offset;
            if (lastTime < SystemClock.uptimeMillis()) {
                lastTime = SystemClock.uptimeMillis() + offset;
            }
            final long tmp = lastTime;
            handler.postAtTime(() -> Log.println(priority, tag, message), tmp);
        }
    }
}
