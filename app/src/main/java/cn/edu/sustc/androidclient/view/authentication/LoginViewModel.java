package cn.edu.sustc.androidclient.view.authentication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.repository.UserRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    // injected modules
    private UserRepository userRepository;

    public LoginViewModel(UserRepository repository) {
        this.userRepository = repository;
    }

    public MutableLiveData<MyResource<Credential>> login(Session session) {
        Logger.d("Attempted to Login: Email: %s, Password: %s", session.email, session.password);
        return userRepository.login(session);
    }

    /**
     * User registration
     *
     * @param session New user
     */
    public MutableLiveData<MyResource<User>> registration(Session session) {
        return userRepository.registration(session);
    }
}
