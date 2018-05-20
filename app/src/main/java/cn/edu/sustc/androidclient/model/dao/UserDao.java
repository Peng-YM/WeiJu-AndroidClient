package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.edu.sustc.androidclient.model.data.User;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);
    @Delete
    void deleteUser(User user);
    @Update
    void updateUser(User user);
    @Query("SELECT * FROM User WHERE userId == :userId")
    User getUserById(String userId);
}
