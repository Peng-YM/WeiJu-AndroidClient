package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
        primaryKeys = {"taskId", "imagePath"},
        foreignKeys = @ForeignKey(entity = Task.class,
        parentColumns = "taskId",
        childColumns = "taskId"))
public class CollectedImage {
    @NonNull
    public String taskId;
    @NonNull
    public String imagePath;

    public CollectedImage(String taskId, String imagePath) {
        this.taskId = taskId;
        this.imagePath = imagePath;
    }
}
