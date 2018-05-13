package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

import io.reactivex.annotations.NonNull;

/**
 * Entity Class to model the relationship between user and task
 */
@Entity
public class UserTaskRecord {
    @PrimaryKey(autoGenerate = true)
    private int recordId;
    private String userId;
    private String taskId;
    private int status;

    public UserTaskRecord(String userId, String taskId, int status) {
        this.userId = userId;
        this.taskId = taskId;
        this.status = status;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class TaskStatus{
        public static final int PROGRESSING = 0;
        public static final int FINISHED = 1;
        public static final int EXPIRED = 2;
        public static final int ACCEPTED = 3;
    }
}
