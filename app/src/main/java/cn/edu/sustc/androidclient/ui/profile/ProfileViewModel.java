package cn.edu.sustc.androidclient.ui.profile;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.model.User;
import jp.wasabeef.glide.transformations.BlurTransformation;

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
    public static void loadAvatar(ImageView imageView, String url){
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

    @BindingAdapter({"myApp:profileBgUrl"})
    public static void loadProfileBackground(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 1)))
                .into(imageView);
    }

    public void goToProfile(View view){
        UserProfileActivity.start(context, user);
    }
}
