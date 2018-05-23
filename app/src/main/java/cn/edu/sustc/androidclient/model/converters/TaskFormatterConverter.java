package cn.edu.sustc.androidclient.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import cn.edu.sustc.androidclient.model.data.Task.TaskFormatter;

/**
 * Convert TaskFormatter object to JSON for room database
 */
public class TaskFormatterConverter {
    @TypeConverter
    public TaskFormatter fromJOSN(String json){
        return new Gson().fromJson(json, TaskFormatter.class);
    }

    @TypeConverter
    public String fromObject(TaskFormatter object){
        return new Gson().toJson(object);
    }
}
