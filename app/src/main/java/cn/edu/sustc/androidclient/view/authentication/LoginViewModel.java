package cn.edu.sustc.androidclient.view.authentication;

import android.arch.lifecycle.LiveData;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.repository.UserRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    // injected modules
    private UserRepository userRepository;

    @Inject
    public LoginViewModel(UserRepository repository) {
        this.userRepository = repository;
    }

    public LiveData<MyResource<Credential>> login(Session session) {
        Logger.d("Attempted to Login: Email: %s, Password: %s", session.email, session.password);
        return userRepository.login(session);
    }

    /**
     * User registration
     *
     * @param session New user
     */
    public LiveData<MyResource<User>> registration(Session session) {
        return userRepository.registration(session);
    }
}
