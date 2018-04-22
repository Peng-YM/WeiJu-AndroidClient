package cn.edu.sustc.androidclient.model;

public class MyResponse<T> {
    public Object meta = null;
    public T data;
    public MyError error;
}
