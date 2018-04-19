package cn.edu.sustc.androidclient.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;

import java.util.UUID;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.service.UserService;
import cn.edu.sustc.androidclient.view.login.LoginStatus;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel{
    // injected modules
    private UserService userService;
    // data
    private MutableLiveData<LoginStatus> status;

    @Inject
    public LoginViewModel(UserService userService){
        this.userService = userService;
        initData();
    }

    private void initData(){
        status = new MutableLiveData<LoginStatus>();
        status.setValue(LoginStatus.NORMAL);
    }

    public void login(Session session){
        Logger.d("Attempted to Login: ");
        Logger.d(session);

        Observer<MyResponse<Credential>> observer = new Observer<MyResponse<Credential>>() {
            @Override
            public void onSubscribe(Disposable d) {
                status.setValue(LoginStatus.PROGRESSING);
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

            @Override
            public void onError(Throwable e) {
                Logger.e("login Failed!\n" + e.getLocalizedMessage());
                status.setValue(LoginStatus.LOGIN_FAILED);
            }

            @Override
            public void onComplete() {
                Logger.d("Login Completed");
                status.setValue(LoginStatus.LOGIN_SUCCESS);
            }
        };

        // TODO: remove fake login
        userService.fakeLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        // login
//        userService.login(session)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
    }

    public void registration(Session session){
        User newUser = new User();
        newUser.id = UUID.randomUUID().toString();
        newUser.email = session.email;
        newUser.password = session.password;

        Logger.d("Attempted to registration");
        Observer<MyResponse<User>> responseSubscriber = new Observer<MyResponse<User>>() {
            @Override
            public void onComplete() {
                Logger.d("Registration Completed");
                status.setValue(LoginStatus.REGISTRATION_SUCCESS);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("Registration Failed");
                status.setValue(LoginStatus.REGISTRATION_FAILED);
            }

            @Override
            public void onSubscribe(Disposable d) {
                status.setValue(LoginStatus.PROGRESSING);
            }

            @Override
            public void onNext(MyResponse<User> userMyResponse) {

            }
        };

        userService.registration(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseSubscriber);
    }

    public MutableLiveData<LoginStatus> getStatus() {
        return status;
    }
}
