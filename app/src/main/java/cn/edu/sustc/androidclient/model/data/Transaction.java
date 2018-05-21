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
    public String userId;
    public String taskId;
}
