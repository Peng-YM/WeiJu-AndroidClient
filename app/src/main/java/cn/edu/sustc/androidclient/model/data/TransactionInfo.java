package cn.edu.sustc.androidclient.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionInfo implements Serializable {
    @SerializedName("user_id")
    public int userId;

    @SerializedName("task_id")
    public int taskId;

    /**
     * Default constructor
     * @param userId user id
     * @param taskId task id
     */
    public TransactionInfo(int userId, int taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }
}
