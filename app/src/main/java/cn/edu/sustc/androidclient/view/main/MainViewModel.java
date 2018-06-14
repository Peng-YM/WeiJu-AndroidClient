package cn.edu.sustc.androidclient.view.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.Constants;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.repository.FileRepository;
import cn.edu.sustc.androidclient.model.repository.UserRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MainViewModel extends BaseViewModel {
    private UserRepository userRepository;
    private FileRepository fileRepository;

    private LiveData<MyResource<User>> currentUser;

    @Inject
    public MainViewModel(UserRepository userRepository, SharedPreferences preferences,
                         FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        // get id from preference
        int userId = preferences.getInt("id", 0);
        currentUser = userRepository.getUserProfile(userId);
    }

    public LiveData<MyResource<User>> getLiveCurrentUser() {
        return currentUser;
    }

    public LiveData<MyResource<User>> updateUserProfile(User newUser) {
        return userRepository.updateUserProfile(newUser);
    }

    public LiveData<MyResource<String>> uploadCover(String imagePath){
        return fileRepository.uploadCover(imagePath);
    }
}
