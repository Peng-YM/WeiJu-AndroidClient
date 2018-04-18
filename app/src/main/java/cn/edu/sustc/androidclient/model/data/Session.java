package cn.edu.sustc.androidclient.model.data;

public class Session{
    public String email;
    public String password;

    public Session(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Email: %s\nPassword: %s\n", email, password);
    }
}
