package cn.edu.sustc.androidclient.ui.login;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.BR;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.data.Credential;
import cn.edu.sustc.androidclient.data.MyResponse;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.ui.main.MainActivity;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends BaseObservable{
    private static final String TAG = "LoginViewModel";

    private boolean hasError;
    private int progressBarVisibility;

    private String email;
    private String password;


    private LoginService loginService;

    public LoginViewModel(){
        Retrofit retrofit = RetrofitFactory.getInstance();
        loginService = retrofit.create(LoginService.class);
        initData();
    }

    private void initData(){
        progressBarVisibility = View.GONE;
    }


    public void login(final View view){
        Session session = new Session(email, password);
        Logger.d("login Button was clicked");
        Logger.d(session);
        Subscriber<MyResponse<Credential>> responseSubscriber = new Subscriber<MyResponse<Credential>>() {
            @Override
            public void onCompleted() {
                Logger.d("login Completed");
                setProgressBarVisibility(View.GONE);
                // start main activity
                Context context = view.getContext();
                MainActivity.start(context);
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("login Failed!\n" + e.getLocalizedMessage());
                setProgressBarVisibility(View.GONE);
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
                editor.commit();
            }
        };
        setProgressBarVisibility(View.VISIBLE);
        // TODO: remove fake login
        loginService.fakeLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseSubscriber);
        // login
//        loginService.login(session)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(responseSubscriber);
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
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
}
