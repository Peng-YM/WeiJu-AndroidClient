package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(
        foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId")}
)
public class Credential {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String userId;
    public String token;

    public Credential(@NonNull String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format("id: %s\ntoken: %s", userId, token);
    }
}
