package cn.edu.sustc.androidclient.data;

public class MyResponse<T> {
    public Object meta = null;
    public T data;
    public MyError error;
}
