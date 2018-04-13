package cn.edu.sustc.androidclient.model;

public class User {
    public String id;
    public String username;
    public String email;
    public long phone;
    public String password;
    public int credit;
    public long balance;
    public int level;
    public String avatar;

    @Override
    public String toString() {
        return String.format("username: %s", username);
    }
}
