package cn.edu.sustc.androidclient.view.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;

import java.util.UUID;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.repository.UserRepository;

public class LoginViewModel extends ViewModel{
    // injected modules
    private UserRepository userRepository;
    // data
    private MutableLiveData<MyResource<Credential>> credential;
    private MutableLiveData<MyResource<User>> createdUser;

    @Inject
    public LoginViewModel(UserRepository repository){
        this.userRepository = repository;
    }

    public void login(Session session) {
        Logger.d("Attempted to Login: ", session);
        credential = userRepository.login(session);
    }

    public void registration(Session session){
        User newUser = new User();
        newUser.id = UUID.randomUUID().toString();
        newUser.email = session.email;
        newUser.password = session.password;

        Logger.d("Attempted to registration");
    }

    public MutableLiveData<MyResource<Credential>> getCredential() {
        return credential;
    }

    public MutableLiveData<MyResource<User>> getCreatedUser() {
        return createdUser;
    }

    /**
     * the mutable data should be clear when the ViewModel is destroyed.
     * */
    @Override
    protected void onCleared(){
        super.onCleared();
        userRepository.onClear();
    }
}
