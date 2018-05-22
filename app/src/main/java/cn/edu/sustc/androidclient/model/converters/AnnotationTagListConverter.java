package cn.edu.sustc.androidclient.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;

/**
 * Convert List<AnnotationTag> tags to JSON object for Room database.
 */
public class AnnotationTagListConverter {
    @TypeConverter
    public ArrayList<AnnotationTag> fromString(String json){
        Type listType = new TypeToken<ArrayList<AnnotationTag>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    @TypeConverter
    public String fromList(ArrayList<AnnotationTag> list){
        return new Gson().toJson(list);
    }
}
