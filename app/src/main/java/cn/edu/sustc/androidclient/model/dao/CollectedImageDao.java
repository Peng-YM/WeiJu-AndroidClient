package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import cn.edu.sustc.androidclient.model.data.CollectedImage;

@Dao
public interface CollectedImageDao {
    @Insert
    void collect(CollectedImage... instances);
    @Query("SELECT * FROM CollectedImage WHERE taskId == :taskId")
    List<CollectedImage> getCollectedImages(String taskId);
    @Delete
    void deleteCollectedImages(CollectedImage... instances);
    @Query("DELETE FROM CollectedImage WHERE taskId == :taskId")
    void deleteAllCollectedImages(String taskId);
}
