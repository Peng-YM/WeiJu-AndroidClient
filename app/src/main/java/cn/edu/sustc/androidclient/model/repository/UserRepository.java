package cn.edu.sustc.androidclient.model.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.rx.AppSchedulerProvider;
import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.MyRequest;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.service.UserService;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UserRepository implements BaseViewModel {
    // injected modules
    private final UserService userService;
    private final AppSchedulerProvider schedulerProvider;
    private final MyDataBase dataBase;
    private final Application application;

    // live data
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<MyResource<Credential>> credential;
    private MutableLiveData<MyResource<User>> userProfile;

    @Inject
    public UserRepository(UserService userService, AppSchedulerProvider schedulerProvider,
                          MyDataBase dataBase, Application application) {
        this.userService = userService;
        this.schedulerProvider = schedulerProvider;
        this.dataBase = dataBase;
        this.application = application;
        initData();
    }

    public MutableLiveData<MyResource<Credential>> login(Session session) {
        MyRequest<Session> sessionMyRequest = new MyRequest<>(session);
        userService.login(sessionMyRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<MyResponse<Credential>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                        MyResource<Credential> resource = MyResource.loading(null);
                        credential.postValue(resource);
                    }

                    @Override
                    public void onSuccess(MyResponse<Credential> response) {
                        MyResource<Credential> resource = MyResource.success(response.data);
                        credential.postValue(resource);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyResource<Credential> resource =
                                MyResource.error(application.getString(R.string.bad_credential), null);
                        credential.postValue(resource);
                        Logger.e(e.getMessage());
                    }
                });
        return credential;
    }

    public MutableLiveData<MyResource<User>> registration(Session session) {
        MutableLiveData<MyResource<User>> userLive = new MutableLiveData<>();
        userLive.postValue(MyResource.loading(null));
        MyRequest<Session> sessionMyRequest = new MyRequest<>(session);
        userService.registration(sessionMyRequest)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new SingleObserver<MyResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(MyResponse<User> response) {
                        userLive.postValue(MyResource.success(response.data));
                    }

                    @Override
                    public void onError(Throwable e) {
                        userLive.postValue(MyResource.error("Registration Failed!", null));
                        Logger.e(e.getMessage());
                    }
                });
        return userLive;
    }

    public MutableLiveData<MyResource<User>> getUserProfile(String id) {
        userProfile = new MutableLiveData<>();
        MyResource<User> resource = MyResource.loading(null);
        userProfile.postValue(resource);
        userService.getProfile(id)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new SingleObserver<MyResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(MyResponse<User> response) {
                        MyResource<User> resource = MyResource.success(response.data);
                        userProfile.postValue(resource);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyResource<User> resource = MyResource.error("Cannot get user profile!", null);
                        userProfile.postValue(resource);
                    }
                });
        return userProfile;
    }

    public void updateUserProfile(User user) {
        userService.updateProfile(user.userId, user)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new SingleObserver<MyResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(MyResponse<User> response) {
                        MyResource<User> resource = MyResource.success(response.data);
                        userProfile.postValue(resource);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyResource<User> resource = MyResource.error("Cannot update profile", null);
                        userProfile.postValue(resource);
                    }
                });
    }

    private void initData() {
        credential = new MutableLiveData<>();
        userProfile = new MutableLiveData<>();
    }

    @Override
    public void onClear() {
        disposables.dispose();
    }
}
