package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.common.base.BaseViewModel;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.service.UserService;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class UserRepository implements BaseViewModel {
    // injected modules
    private final UserService userService;
    private final AppSchedulerProvider schedulerProvider;

    // live data
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<MyResource<Credential>> credential;
    private MutableLiveData<MyResource<User>> userProfile;

    @Inject
    public UserRepository(UserService userService, AppSchedulerProvider schedulerProvider){
        this.userService = userService;
        this.schedulerProvider = schedulerProvider;
        initData();
    }

    public void getProfile(String id, SingleObserver<MyResponse<User>> observer) {
        this.userService
            .getProfile(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer);
    }

    public MutableLiveData<MyResource<Credential>> login(Session session) {
        // TODO: remove fake login
        userService.fakeLogin()
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
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
                        MyResource<Credential> resource = MyResource.error("Bad Credential!", null);
                        credential.postValue(resource);
                    }
                });
        return credential;
    }

    public MutableLiveData<MyResource<Credential>> registration() {
        return credential;
    }

    public MutableLiveData<MyResource<User>> getUserProfile(String id) {
        userProfile = new MutableLiveData<>();
        userService.getProfile(id)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new SingleObserver<MyResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                        MyResource<User> resource = MyResource.loading(null);
                        userProfile.postValue(resource);
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

    private void initData(){
        credential = new MutableLiveData<>();
        userProfile = new MutableLiveData<>();
    }

    @Override
    public void onClear() {
        disposables.dispose();
    }
}
