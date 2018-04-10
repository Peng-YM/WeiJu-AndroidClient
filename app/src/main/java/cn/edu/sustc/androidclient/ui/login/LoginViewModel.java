package cn.edu.sustc.androidclient.ui.login;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.BR;
import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.rest.LoginService;
import cn.edu.sustc.androidclient.ui.main.MainActivity;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends BaseObservable{
    private static final String TAG = "LoginViewModel";

    // data binding
    private int progressBarVisibility;
    private String email;
    private String password;

    private AlertDialog alertDialog;
    private LoginService loginService;
    private Context context;
    private LoginActivity activity;

    public LoginViewModel(Context context){
        this.context = context;
        Retrofit retrofit = RetrofitFactory.getInstance();
        loginService = retrofit.create(LoginService.class);
        initData();
//        Logger.d(activity.test);
    }

    private void initData(){
        progressBarVisibility = View.GONE;
    }


    public void login(View view){
        Session session = new Session(email, password);

        Logger.d("login Button was clicked");
        Logger.d(session);

        Subscriber<MyResponse<Credential>> responseSubscriber = new Subscriber<MyResponse<Credential>>() {
            @Override
            public void onCompleted() {
                Logger.d("login Completed");
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
            loginService.fakeLogin()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseSubscriber);
            // login
//            loginService.login(session)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(responseSubscriber);
        }else{
            alertDialog.setMessage(context.getResources().getString(R.string.alert_field_empty));
            alertDialog.show();
        }

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
