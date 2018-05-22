package cn.edu.sustc.androidclient.model.data;

import com.google.gson.Gson;

public class Session {
    public String email;
    public String password;

    public Session(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
