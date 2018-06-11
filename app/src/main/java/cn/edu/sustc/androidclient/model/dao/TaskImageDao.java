package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.edu.sustc.androidclient.model.data.TaskImage;
import io.reactivex.Flowable;

@Dao
public interface TaskImageDao {
    @Insert
    void collect(TaskImage... instances);

    @Query("SELECT * FROM TaskImage WHERE transactionId == :taskId")
    Flowable<List<TaskImage>> getCollectedImages(String taskId);

    @Delete
    void deleteCollectedImages(TaskImage... instances);

    @Query("DELETE FROM TaskImage WHERE transactionId == :taskId")
    void deleteAllCollectedImages(String taskId);
}
