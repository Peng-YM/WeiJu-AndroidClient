package cn.edu.sustc.androidclient.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import cn.edu.sustc.androidclient.model.data.Task;

public class FormatterConverter {
    @TypeConverter
    public static Task.TaskFormatter fromJOSN(String json){
        return new Gson().fromJson(json, Task.TaskFormatter.class);
    }

    @TypeConverter
    public static String toJSON(Task.TaskFormatter formatter){
        return new Gson().toJson(formatter);
    }
}
