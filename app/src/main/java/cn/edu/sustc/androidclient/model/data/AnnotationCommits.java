package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import static cn.edu.sustc.androidclient.view.task.annotationtask.Shape.Coordinate;

@Entity(
        primaryKeys = {"userId", "transactionId"},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId"),
                @ForeignKey(entity = Transaction.class,
                        parentColumns = "transactionId",
                        childColumns = "transactionId")
        }
)
public class AnnotationCommits implements Serializable {
    @ColumnInfo(index = true)
    @NonNull
    public String transactionId;

    @ColumnInfo(index = true)
    @NonNull
    public String userId;
    public List<AnnotationTag> tags;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class AnnotationTag implements Serializable {
        public List<Coordinate> positions;
        public String description;
        public String name;
        public List<Attribute> attributes;
    }

    public static class Attribute implements Serializable {
        public String name;
        public String description;
        public String type;
        public List<String> options;
        public List<String> values;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public static class AttributeType {
        public final static String SINGLE_OPTION = "single-option";
        public final static String MULTI_OPTION = "multi-option";
        public final static String NUMBER = "number";
        public final static String BOOLEAN = "boolean";
        public final static String STRING = "string";
    }
}
