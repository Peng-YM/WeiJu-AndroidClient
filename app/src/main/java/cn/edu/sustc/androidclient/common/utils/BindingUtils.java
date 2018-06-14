package cn.edu.sustc.androidclient.common.utils;


import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;

public final class BindingUtils {
    private BindingUtils(){}

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Logger.v("Fetch Image from url: %s", url);
        /*对于不同的图片在不同情况下的替代*/
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.logo) // 全局型的
                .error(R.drawable.ic_load_error);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
