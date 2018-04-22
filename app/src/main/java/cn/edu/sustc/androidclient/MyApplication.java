package cn.edu.sustc.androidclient;

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

import cn.edu.sustc.androidclient.common.utils.SharePreferenceHelper;
import cn.edu.sustc.androidclient.di.DaggerMyAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

// application class which is responsible to initialize global variables
public class MyApplication extends DaggerApplication {
    private static String PACKAGE_NAME;

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
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerMyAppComponent.builder().create(this);
    }

    private class LogCatStrategy implements LogStrategy {


        private Handler handler;
        private long lastTime = SystemClock.uptimeMillis();
        private long offset = 5;

        public LogCatStrategy() {
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
            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    Log.println(priority, tag, message);
                }
            }, tmp);
        }
    }
}
