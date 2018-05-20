package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.edu.sustc.androidclient.model.data.UserTaskRecord;

@Dao
public interface UserTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void takeTask(UserTaskRecord... records);

    @Update
    void updateRecord(UserTaskRecord... records);

    @Delete
    void deleteRecord(UserTaskRecord... records);

    @Query("SELECT * FROM UserTaskRecord WHERE userId == :userId AND status == :status")
    List<UserTaskRecord> getRecordsByStatus(String userId, int status);

    @Query("DELETE FROM UserTaskRecord WHERE userId == :userId")
    void deleteAllUserRecord(String userId);

    @Query("SELECT * FROM UserTaskRecord WHERE userId == :userId")
    List<UserTaskRecord> getAllUserRecord(String userId);

    @Query("SELECT * FROM UserTaskRecord WHERE userId == :userId AND taskId == :taskId")
    UserTaskRecord getRecordByTaskId(int userId, int taskId);
}