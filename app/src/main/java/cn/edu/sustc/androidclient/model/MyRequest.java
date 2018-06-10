package cn.edu.sustc.androidclient.model;

public class MyRequest<T> {
    public T data;

    public MyRequest(T data) {
        this.data = data;
    }
}
