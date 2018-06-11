package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;

@Entity(
        indices = {
                @Index(value = {"taskId"}, unique = true)
        }
)
public class Task implements Serializable {
    @SerializedName("id")
    @PrimaryKey
    @NonNull
    public int taskId;
    public String name;

    @SerializedName("start_time")
    public String start;

    @SerializedName("deadline")
    public String end;

    public String description;
    public String author;
    public int type;
    public String cover;
//    public TaskFormatter formatter;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class TaskType {
        public final static int COLLECTION = 0;
        public final static int ANNOTATION = 1;
    }

    public static class TaskFormatter implements Serializable {
        public List<AnnotationTag> tags;
    }
}
