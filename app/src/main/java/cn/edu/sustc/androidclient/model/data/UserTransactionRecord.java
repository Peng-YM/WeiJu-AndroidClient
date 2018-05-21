package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static java.lang.String.format;

/**
 * Entity Class to model the relationship between user and task
 */
@Entity(
        indices = {@Index(value = {"userId", "transactionId"}, unique = true)},
        primaryKeys = {"userId", "transactionId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Transaction.class,
                        parentColumns = "transactionId",
                        childColumns = "transactionId"
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId"
                )
        }
)
public class UserTransactionRecord {
    @NonNull
    public String userId;
    @NonNull
    public String transactionId;
    public int status;

    public UserTransactionRecord(@NonNull String userId, @NonNull String transactionId, int status) {
        this.userId = userId;
        this.transactionId = transactionId;
        this.status = status;
    }

    public static class TransactionStatus {
        public static final int PROGRESSING = 0;
        public static final int FINISHED = 1;
        public static final int EXPIRED = 2;
        public static final int ACCEPTED = 3;
    }

    @Override
    public String toString() {
        return format("userId: %s\n transactionId: %s\n status: %s\n", userId, transactionId, status);
    }
}
