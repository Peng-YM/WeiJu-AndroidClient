package cn.edu.sustc.androidclient.model.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;

/**
 * Convert List<AnnotationTag> tags to JSON object for Room database.
 */
public class AnnotationTagListConverter {
    @TypeConverter
    public static List<AnnotationTag> fromString(String json){
        Type listType = new TypeToken<List<AnnotationTag>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    @TypeConverter
    public static String fromList(List<AnnotationTag> list){
        return new Gson().toJson(list);
    }
}
