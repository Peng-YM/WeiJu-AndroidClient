package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

@Entity(
        primaryKeys = {"transactionId", "imagePath"},
        foreignKeys = {
                @ForeignKey(
                        entity = Transaction.class,
                        parentColumns = "transactionId",
                        childColumns = "transactionId"
                )
        }
)
public class TaskImage {
    @NonNull
    public String transactionId;
    @NonNull
    public String imagePath;

    public TaskImage(String transactionId, String imagePath) {
        this.transactionId = transactionId;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
