package cn.edu.sustc.androidclient.rest.impl;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.rest.UserService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserServiceImpl{
    private static UserServiceImpl instance;
    private UserService service;
    private UserServiceImpl(UserService service){
        this.service = service;
    }

    public static UserServiceImpl getInstance(){
        if (instance == null){
            synchronized (UserServiceImpl.class){
                if (instance == null){
                    instance = new UserServiceImpl(
                            RetrofitFactory.getInstance().create(UserService.class)
                    );
                }
            }
        }
        return instance;
    }

    public void login(Session session, Subscriber<MyResponse<Credential>> subscriber){
        this.service
            .login(session)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
    }


    public void getProfile(String id, Subscriber<MyResponse<User>> subscriber) {
        this.service
            .getProfile(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
    }
}
