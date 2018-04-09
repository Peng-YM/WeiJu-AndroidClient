package cn.edu.sustc.androidclient.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;


public class LoggerHelper {
    private static volatile AndroidLogAdapter instance;

    private LoggerHelper() {
    }

    public static AndroidLogAdapter getAdapter() {
        if (instance == null) {
            synchronized (LoggerHelper.class) {
                if (instance == null) {
                    PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                            .showThreadInfo(false)
                            .logStrategy(new LogCatStrategy())
                            .methodCount(1)
                            .build();
                    instance = new AndroidLogAdapter(strategy){
                        @Override
                        public boolean isLoggable(int priority, @Nullable String tag) {
                            return BuildConfig.DEBUG;
                        }
                    };
                }
            }
        }
        return instance;
    }

    static class LogCatStrategy implements LogStrategy {


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
