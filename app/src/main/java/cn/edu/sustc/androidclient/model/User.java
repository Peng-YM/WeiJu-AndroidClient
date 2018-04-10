package cn.edu.sustc.androidclient.model;

public class User {
    public String id;
    public String username;
    public String email;
    public long phone;
    public int credit;
    public long balance;
    public String nickname;
    public int level;
    public String avatar;

    @Override
    public String toString() {
        return String.format("username: %s", username);
    }
}
