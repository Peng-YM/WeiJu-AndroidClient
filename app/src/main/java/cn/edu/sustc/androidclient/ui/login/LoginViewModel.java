package cn.edu.sustc.androidclient.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;

import java.util.UUID;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.service.UserService;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends ViewModel{
    private UserService userService;
    private MutableLiveData<LoginStatus> status;

    public LoginViewModel(){
        Retrofit retrofit = RetrofitFactory.getInstance();
        userService = retrofit.create(UserService.class);
        initData();
    }

    private void initData(){
        status = new MutableLiveData<LoginStatus>();
        status.setValue(LoginStatus.NORMAL);
    }

    public void login(Session session){
        Logger.d("Attempted to Login: ");
        Logger.d(session);

        Subscriber<MyResponse<Credential>> responseSubscriber = new Subscriber<MyResponse<Credential>>() {
            @Override
            public void onCompleted() {
                Logger.d("Login Completed");
                status.setValue(LoginStatus.LOGIN_SUCCESS);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("login Failed!\n" + e.getLocalizedMessage());
                status.setValue(LoginStatus.LOGIN_FAILED);
            }

            @Override
            public void onNext(MyResponse<Credential> response) {
                Credential credential = response.data;
                Logger.d("Get credential: \n" + credential.toString());
                // save credential
                SharedPreferences preferences = SharePreferenceHelper.getPreferences();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id", credential.id);
                editor.putString("token", credential.token);
                editor.apply();
            }
        };
        status.setValue(LoginStatus.PROGRESSING);
        // TODO: remove fake login
        userService.fakeLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseSubscriber);
        // login
//        userService.login(session)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(responseSubscriber);
    }

    public void registration(Session session){
        User newUser = new User();
        newUser.id = UUID.randomUUID().toString();
        newUser.email = session.email;
        newUser.password = session.password;

        Logger.d("Attempted to registration");
        Subscriber<MyResponse<User>> responseSubscriber = new Subscriber<MyResponse<User>>() {
            @Override
            public void onCompleted() {
                Logger.d("Registration Completed");
                status.setValue(LoginStatus.REGISTRATION_SUCCESS);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("Registration Failed");
                status.setValue(LoginStatus.REGISTRATION_FAILED);
            }

            @Override
            public void onNext(MyResponse<User> userMyResponse) {

            }
        };

        status.setValue(LoginStatus.PROGRESSING);
        userService.registration(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseSubscriber);
    }

    public MutableLiveData<LoginStatus> getStatus() {
        return status;
    }
}
