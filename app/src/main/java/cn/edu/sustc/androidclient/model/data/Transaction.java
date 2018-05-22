package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = User.class,
                    parentColumns = "userId",
                    childColumns = "userId"
                ),
                @ForeignKey(entity = Task.class,
                        parentColumns = "taskId",
                        childColumns = "taskId"
                )
        }
)
public class Transaction implements Serializable{
    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true)
    public String transactionId;
    @NonNull
    public String userId;
    @NonNull
    public String taskId;
    public int status;

    public Transaction(@NonNull String transactionId,
                       @NonNull String userId,
                       @NonNull String taskId,
                       int status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
    }

    public static class TransactionStatus {
        public static final int PROGRESSING = 0;
        public static final int FINISHED = 1;
        public static final int EXPIRED = 2;
        public static final int ACCEPTED = 3;
    }
}
