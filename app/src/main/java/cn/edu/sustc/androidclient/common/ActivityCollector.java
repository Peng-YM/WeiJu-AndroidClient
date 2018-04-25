package cn.edu.sustc.androidclient.common;

import android.app.Activity;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        Logger.v("Add activity: %s[%s]", activity.getLocalClassName(), activity);
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        Logger.v("Remove activity: %s[%s]", activity.getLocalClassName(), activity);
        activities.remove(activity);
    }

    public static void finishAll() {
        Logger.v("Kill all activities");
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
