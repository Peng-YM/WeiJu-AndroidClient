package cn.edu.sustc.androidclient.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Commits {
    public String id;
    @SerializedName("task_id")
    public String taskId;
    @SerializedName("author_id")
    public String authorId;
    public List<Result> results;

    static class Result{
        @SerializedName("picture_url")
        String pictureUrl;
        String xml;
    }
}
