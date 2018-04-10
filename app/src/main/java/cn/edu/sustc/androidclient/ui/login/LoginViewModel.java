package cn.edu.sustc.androidclient.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.UUID;

import cn.edu.sustc.androidclient.BR;
import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.rest.UserService;
import cn.edu.sustc.androidclient.ui.main.MainActivity;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends BaseObservable{
    // data binding
    private int progressBarVisibility;
    private String email;
    private String password;

    private AlertDialog alertDialog;
    private UserService userService;
    private Context context;
    private LoginActivity activity;

    public LoginViewModel(Context context){
        this.context = context;
        Retrofit retrofit = RetrofitFactory.getInstance();
        userService = retrofit.create(UserService.class);
        initData();
    }

    private void initData(){
        progressBarVisibility = View.GONE;
    }


    public void login(View view){
        Session session = new Session(email, password);

        Logger.d("Attempted to Login: ");
        Logger.d(session);

        Subscriber<MyResponse<Credential>> responseSubscriber = new Subscriber<MyResponse<Credential>>() {
            @Override
            public void onCompleted() {
                Logger.d("Login Completed");
                setProgressBarVisibility(View.GONE);
                // start main activity
                MainActivity.start(context);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("login Failed!\n" + e.getLocalizedMessage());
                setProgressBarVisibility(View.GONE);
                alertDialog.setMessage(context.getResources().getString(R.string.alert_bad_credential));
                alertDialog.show();
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
        if (notEmpty()){
            setProgressBarVisibility(View.VISIBLE);
            // TODO: remove fake login
            userService.fakeLogin()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseSubscriber);
            // login
//            userService.login(session)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(responseSubscriber);
        }else{
            alertDialog.setMessage(context.getResources().getString(R.string.alert_field_empty));
            alertDialog.show();
        }

    }

    public void registration(View view){
        User newUser = new User();
        newUser.id = UUID.randomUUID().toString();
        newUser.email = email;
        newUser.password = password;

        Logger.d("Attempted to registration");
        Subscriber<MyResponse<User>> responseSubscriber = new Subscriber<MyResponse<User>>() {
            @Override
            public void onCompleted() {
                Logger.d("Registration Completed");
                setProgressBarVisibility(View.GONE);
                LoginActivity.start(context);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("Registration Failed");
                setProgressBarVisibility(View.GONE);
                alertDialog.setMessage(context.getResources().getString(R.string.alert_registration_failed));
                alertDialog.show();
            }

            @Override
            public void onNext(MyResponse<User> userMyResponse) {

            }
        };
        if(notEmpty()){
            setProgressBarVisibility(View.VISIBLE);
            userService.registration(newUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseSubscriber);
        }else{
            alertDialog.setMessage(context.getResources().getString(R.string.alert_field_empty));
            alertDialog.show();
        }

    }


    public void goToRegistration(View view){
        RegistrationActivity.start(context);
    }

    @Bindable
    public int getProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(int progressBarVisibility) {
        this.progressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    private boolean notEmpty(){
        return email != null && password != null && !email.isEmpty() && !password.isEmpty();
    }
}
