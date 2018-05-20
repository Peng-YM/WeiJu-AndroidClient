package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(
        primaryKeys = {"taskId"}
)
public class Task implements Serializable {
    @SerializedName("id")
    @NonNull
    public String taskId;
    public String title;
    public String start;
    public String end;
    public String descriptions;
    public String author;
    public int type;
    public String cover;
    public TaskFormatter formatter;

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
        public List<Tag> tags;
    }

    public static class Tag implements Serializable {
        public String name;
        public String description;
        public List<Attribute> attributes;
    }

    public static class Attribute implements Serializable {
        public String name;
        public String description;
        public List<String> values;
    }

}
