package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

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
public class CollectedImage {
    @NonNull
    public String transactionId;
    @NonNull
    public String imagePath;

    public CollectedImage(String transactionId, String imagePath) {
        this.transactionId = transactionId;
        this.imagePath = imagePath;
    }
}
