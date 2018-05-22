package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.edu.sustc.androidclient.model.data.User;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);
    @Delete
    void deleteUser(User user);
    @Update
    void updateUser(User user);

    /**
     * get user by id
     * 1. if there is no user in the database, Single will trigger onError
     * 2. if there is a user, Single will trigger onSuccess
     * 3. if the user is updated, nothing gonna happen
     * @param userId user id
     * @return User
     */
    @Query("SELECT * FROM User WHERE userId == :userId")
    Single<User> getUserById(String userId);
}
