package cn.edu.sustc.androidclient.repository;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.service.UserService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UserRepository {
    private static UserRepository instance;
    private UserService userService;
    private UserRepository(UserService userService){
        this.userService = userService;
    }

    public static UserRepository getInstance(){
        if (instance == null){
            synchronized (UserRepository.class){
                if (instance == null){
                    instance = new UserRepository(
                            RetrofitFactory.getInstance().create(UserService.class)
                    );
                }
            }
        }
        return instance;
    }

    public void login(Session session, Observer<MyResponse<Credential>> observer){
        this.userService
            .login(session)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer);
    }


    public void getProfile(String id, Observer<MyResponse<User>> observer) {
        this.userService
            .getProfile(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer);
    }
}
