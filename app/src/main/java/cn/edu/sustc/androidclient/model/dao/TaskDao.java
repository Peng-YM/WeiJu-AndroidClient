package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.edu.sustc.androidclient.model.data.Task;
import io.reactivex.Single;

/**
 * Notice that only the tasks applied by user will be stored.
 * Other tasks will not be saved in database.
 */
@Dao
public interface TaskDao {
    @Insert
    void saveTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM Task WHERE taskId == :taskId")
    Single<Task> getTask(String taskId);
}
