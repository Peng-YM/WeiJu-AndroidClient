package cn.edu.sustc.androidclient.view.main;

import android.content.SharedPreferences;

import cn.edu.sustc.androidclient.model.repository.UserRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    MainViewModel provideMainViewModel(UserRepository repository, SharedPreferences sharedPreferences){
        return new MainViewModel(repository, sharedPreferences);
    }
}
