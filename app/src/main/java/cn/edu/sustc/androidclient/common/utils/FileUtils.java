package cn.edu.sustc.androidclient.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {
    public static File getRootPath(Context context) {
        if (FileUtils.sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return context.getFilesDir();
        }
    }

    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else return false;
    }
}