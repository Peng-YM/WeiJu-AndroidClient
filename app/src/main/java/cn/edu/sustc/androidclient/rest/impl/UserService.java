package cn.edu.sustc.androidclient.rest.impl;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.rest.UserAPI;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class UserService {
    private static UserService instance;
    private UserAPI userAPI;
    private UserService(UserAPI userAPI){
        this.userAPI = userAPI;
    }

    public static UserService getInstance(){
        if (instance == null){
            synchronized (UserService.class){
                if (instance == null){
                    instance = new UserService(
                            RetrofitFactory.getInstance().create(UserAPI.class)
                    );
                }
            }
        }
        return instance;
    }

    public void login(Session session, Subscriber<MyResponse<Credential>> subscriber){
        this.userAPI
            .login(session)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
    }


    public void getProfile(String id, Subscriber<MyResponse<User>> subscriber) {
        this.userAPI
            .getProfile(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
    }
}
