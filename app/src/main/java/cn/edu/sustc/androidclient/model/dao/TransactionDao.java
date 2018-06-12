package cn.edu.sustc.androidclient.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.edu.sustc.androidclient.model.data.Transaction;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TransactionDao {
    @Insert
    void addTransaction(Transaction transaction);

    @Update
    void updateTransaction(Transaction transaction);

    @Query("SELECT * FROM `Transaction` WHERE transactionId == :transactionId")
    Transaction findById(int transactionId);

    /**
     * find all transaction of a user
     * every time the transaction updated, the Flowable will emit automatically
     *
     * @param userId user id
     * @return a list of transactions
     */
    @Query("SELECT * FROM `Transaction` WHERE userId == :userId")
    Flowable<List<Transaction>> findAllTransactions(int userId);

    @Query("SELECT * FROM `Transaction` WHERE transactionId == :transactionId")
    Single<Transaction> findTransactionById(int transactionId);

    @Query("SELECT * FROM `Transaction` WHERE userId == :userId AND taskId == :taskId")
    Transaction findUnfinishedTask(int userId, int taskId);
}
