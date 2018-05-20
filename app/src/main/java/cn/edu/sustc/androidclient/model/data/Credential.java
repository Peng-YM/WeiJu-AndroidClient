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
    private String userId;
    private String token;

    public Credential(@NonNull String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return String.format("id: %s\ntoken: %s", userId, token);
    }
}
