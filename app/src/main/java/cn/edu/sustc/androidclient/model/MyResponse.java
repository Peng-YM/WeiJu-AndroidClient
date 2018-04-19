package cn.edu.sustc.androidclient.model;

import cn.edu.sustc.androidclient.model.MyError;

public class MyResponse<T> {
    public Object meta = null;
    public T data;
    public MyError error;
}
