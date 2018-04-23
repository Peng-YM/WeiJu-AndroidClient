package cn.edu.sustc.androidclient.model.data;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.edu.sustc.androidclient.R;

public class Task implements Serializable {
    public String id;
    public String title;
    public String start;
    public String end;
    public String descriptions;
    public String author;
    public List<String> pictures;
    @SerializedName("type")
    public TaskType type;
    public TaskFormatter formatter;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public enum TaskType implements Serializable{
        @SerializedName("0")
        COLLECTION(0, R.string.collection_task),
        @SerializedName("1")
        ANNOTATION(1, R.string.annotation_task);

        private int type;
        private int description;

        TaskType(int type, int description){
            this.type = type;
            this.description = description;
        }

        public int getType() {
            return type;
        }

        public int getDescription() {
            return description;
        }
    }

    public static class TaskFormatter implements Serializable{
        public List<Tag> tags;
    }

    public static class Tag implements Serializable{
        public String name;
        public String description;
        public List<Attribute> attributes;
    }

    public static class Attribute implements Serializable{
        public String name;
        public String description;
        public List<String> values;
    }
}
