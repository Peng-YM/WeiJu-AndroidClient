package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static java.lang.String.format;

/**
 * Entity Class to model the relationship between user and task
 */
@Entity(
        indices = {@Index(value = {"userId", "taskId"}, unique = true)},
        primaryKeys = {"userId", "taskId"}
//        foreignKeys = {
//                @ForeignKey(
//                        entity = Task.class,
//                        parentColumns = "taskId",
//                        childColumns = "taskId"
//                ),
//                @ForeignKey(
//                        entity = User.class,
//                        parentColumns = "userId",
//                        childColumns = "userId"
//                )
//        }
)
public class UserTaskRecord {
    @NonNull
    public String userId;
    @NonNull
    public String taskId;
    public int status;

    public UserTaskRecord(@NonNull String userId, @NonNull String taskId, int status) {
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
    }

    public static class TaskStatus {
        public static final int PROGRESSING = 0;
        public static final int FINISHED = 1;
        public static final int EXPIRED = 2;
        public static final int ACCEPTED = 3;
    }

    @Override
    public String toString() {
        return format("userId: %s\n taskId: %s\n status: %s\n", userId, taskId, status);
    }
}
