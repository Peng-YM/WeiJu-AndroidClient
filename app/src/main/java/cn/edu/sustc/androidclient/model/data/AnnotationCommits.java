package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
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
        },
        indices = {@Index(value = {"userId", "transactionId"}, unique = true)}
)
public class AnnotationCommits implements Serializable{
    @NonNull
    public String transactionId;
    @NonNull
    public String userId;
    // TODO: add type converter
    @Ignore
    public List<AnnotationTag> tags;
    public static class AnnotationTag implements Serializable{
        public List<Coordinate> criticalPoints;
        public String name;
        public List<Attribute> attributes;
    }
    public static class Attribute implements Serializable {
        public String name;
        public List<String> values;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
