package cn.edu.sustc.androidclient.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.repository.UserRepository;

public class MainViewModel extends ViewModel {
    private UserRepository userRepository;
    private SharedPreferences preferences;

    private MutableLiveData<MyResource<User>> currentUser;

    @Inject
    public MainViewModel(UserRepository userRepository, SharedPreferences preferences){
        this.userRepository = userRepository;
        this.preferences = preferences;
        // get id from preference
        String id = preferences.getString("id", "");
        currentUser = userRepository.getUserProfile(id);
    }

    public LiveData<MyResource<User>> getLiveCurrentUser() {
        Logger.d("Current user: ", currentUser);
        return currentUser;
    }
}
