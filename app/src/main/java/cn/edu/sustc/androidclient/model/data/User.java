package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Credential.class,
                        parentColumns = "userId",
                        childColumns = "userId"
                )
        }
)
public class User implements Serializable {
    @SerializedName("user_id")
    @NonNull
    @PrimaryKey
    public int userId;
    public String username;
    public String email;
    public String phone;
    public String password;
    public int credit;
    public long balance;
    public int level;
    public String avatar;

    public User(@NonNull int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
