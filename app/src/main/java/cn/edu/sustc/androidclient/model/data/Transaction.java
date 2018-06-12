package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId"
                )
        }
)
public class Transaction implements Serializable {
    @PrimaryKey
    @SerializedName("commit_id")
    public int transactionId;

    @ColumnInfo(index = true)
    @NonNull
    @SerializedName("user_id")
    public int userId;

    @ColumnInfo(index = true)
    @NonNull
    @SerializedName("task_id")
    public int taskId;

    public int status;

    public List<String> pictures;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static class TransactionStatus {
        public static final int PROGRESSING = 0;
        public static final int FINISHED = 1;
        public static final int EXPIRED = 2;
        public static final int ACCEPTED = 3;
    }
}
