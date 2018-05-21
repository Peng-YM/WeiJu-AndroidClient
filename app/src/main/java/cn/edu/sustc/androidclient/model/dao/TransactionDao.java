package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import cn.edu.sustc.androidclient.model.data.Transaction;

@Dao
public interface TransactionDao {
    @Insert
    void applyTask(Transaction transaction);
}
