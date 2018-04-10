package cn.edu.sustc.androidclient.model;

public class Credential {
    public String id;
    public String token;

    @Override
    public String toString() {
        return String.format("id: %s\ntoken: %s", id, token);
    }
}
