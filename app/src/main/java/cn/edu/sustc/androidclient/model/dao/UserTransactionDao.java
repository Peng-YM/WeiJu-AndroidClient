package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.edu.sustc.androidclient.model.data.UserTransactionRecord;

@Dao
public interface UserTransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void takeTask(UserTransactionRecord... records);

    @Update
    void updateRecord(UserTransactionRecord... records);

    @Delete
    void deleteRecord(UserTransactionRecord... records);

    @Query("SELECT * FROM UserTransactionRecord WHERE userId == :userId AND status == :status")
    List<UserTransactionRecord> getRecordsByStatus(String userId, int status);

    @Query("DELETE FROM UserTransactionRecord WHERE userId == :userId")
    void deleteAllUserRecord(String userId);

    @Query("SELECT * FROM UserTransactionRecord WHERE userId == :userId")
    List<UserTransactionRecord> getAllUserRecord(String userId);

    @Query("SELECT * FROM UserTransactionRecord WHERE userId == :userId AND transactionId == :taskId")
    UserTransactionRecord getRecordByTaskId(int userId, int taskId);
}