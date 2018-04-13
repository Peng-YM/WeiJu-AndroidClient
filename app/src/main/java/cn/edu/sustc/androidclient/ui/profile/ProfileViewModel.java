package cn.edu.sustc.androidclient.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.SharePreferenceHelper;
import cn.edu.sustc.androidclient.model.User;
import cn.edu.sustc.androidclient.rest.UserService;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileViewModel extends BaseObservable {
    private User user;
    private Context context;

    public ProfileViewModel(Context context, User user){
        this.context = context;
        this.user = user;
    }

    @Bindable
    public User getUser() {
        return user;
    }

    @BindingAdapter({"myApp:imageUrl"})
    public void loadAvatar(ImageView imageView, String url){
        Logger.d("Load Avatar from %s", url);
        RequestOptions options = new RequestOptions()
            .circleCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.ic_load_error);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void goToProfile(){
        UserProfileActivity.start(context);
    }
}
