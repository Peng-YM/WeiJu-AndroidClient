package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.edu.sustc.androidclient.model.data.UserTaskRecord;

@Dao
public interface UserTaskDao {
    @Insert
    void takeTask(UserTaskRecord record);

    @Update
    void updateRecord(UserTaskRecord record);

    @Delete
    void deleteRecord(UserTaskRecord record);

    @Query("SELECT * FROM UserTaskRecord WHERE recordId = :recordId")
    UserTaskRecord getRecordById(int recordId);

    @Query("SELECT * FROM UserTaskRecord WHERE status = :status")
    List<UserTaskRecord> getRecordsByStatus(int status);
}
