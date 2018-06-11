package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@Entity(
        indices = {@Index(value = "userId", unique = true)}
)
public class Credential {
    @PrimaryKey
    @NonNull
    @SerializedName("user_id")
    public int userId;

    public String token;

    public Credential(@NonNull int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
