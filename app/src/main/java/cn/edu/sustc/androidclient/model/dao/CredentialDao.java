package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Credential;
import io.reactivex.Single;
import io.reactivex.SingleSource;

@Dao
public interface CredentialDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCredential(Credential credential);

    @Delete
    void deleteCredential(Credential credential);

    @Update
    void updateCredential(Credential credential);

    @Query("SELECT * FROM Credential WHERE userId == :userId")
    Single<Credential> getUserCredential(String userId);
}


