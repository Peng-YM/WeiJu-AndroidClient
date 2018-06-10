package cn.edu.sustc.androidclient.view.authentication;

import cn.edu.sustc.androidclient.model.repository.UserRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticationModule {
    @Provides
    LoginViewModel provideLoginViewModel(UserRepository repository) {
        return new LoginViewModel(repository);
    }
}
