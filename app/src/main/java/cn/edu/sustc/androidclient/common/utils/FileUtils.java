package cn.edu.sustc.androidclient.common.utils;

import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class FileUtils {
    private FileUtils(){}

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

    public static String readAssetFile(Context context, String path){
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(path);
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null){
                builder.append(str);
            }
            in.close();
            is.close();
        }catch (IOException e){
            Logger.e("Cannot open asset file from: %s", path);
            e.printStackTrace();
        }
        return builder.toString();
    }
}