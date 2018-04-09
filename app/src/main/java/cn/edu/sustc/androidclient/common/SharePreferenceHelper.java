package cn.edu.sustc.androidclient.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceHelper {
    private static volatile SharedPreferences preferences;
    public static void setPreference(SharedPreferences prefs){
        preferences = prefs;
    }
    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
