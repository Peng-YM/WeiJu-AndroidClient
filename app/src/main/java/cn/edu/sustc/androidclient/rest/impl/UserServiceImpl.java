package cn.edu.sustc.androidclient.rest.impl;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.rest.UserService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserServiceImpl{
    private static UserServiceImpl instance;
    private UserServiceImpl(){}

    public static UserServiceImpl getInstance(){
        if (instance == null){
            synchronized (UserServiceImpl.class){
                if (instance == null){
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    public void getProfile(String id, Subscriber<MyResponse<User>> subscriber) {
        RetrofitFactory.getInstance()
                .create(UserService.class)
                .getProfile(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
