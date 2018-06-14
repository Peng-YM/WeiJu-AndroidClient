package cn.edu.sustc.androidclient.model.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;

public class Task implements Serializable {
    @SerializedName("id")
    public int taskId;
    public String name;

    @SerializedName("start_time")
    public String start;

    @SerializedName("deadline")
    public String end;

    public String description;

    @SerializedName("user_id")
    public int author;

    public int type;
    public String cover;
//    public TaskFormatter formatter;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class TaskType {
        public final static int COLLECTION = 1;
        public final static int ANNOTATION = 0;
    }

    public static class TaskFormatter implements Serializable {
        public List<AnnotationTag> tags;
    }
}
