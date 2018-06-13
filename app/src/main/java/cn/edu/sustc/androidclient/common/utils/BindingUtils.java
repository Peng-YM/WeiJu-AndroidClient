package cn.edu.sustc.androidclient.common.utils;


import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;

import cn.edu.sustc.androidclient.R;

public final class BindingUtils {
    private BindingUtils(){}

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Logger.v("Fetch Image from url: %s", url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.ic_load_error);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void selectImage(Context context, ImageView imageView){
        Album.image(context)
                .singleChoice()
                .camera(true)
                .onResult((requestCode, result) -> {
                    AlbumFile selected = result.get(0);
                    Logger.d("You selected " + selected.getPath());
                    RequestOptions options = new RequestOptions()
                            .centerCrop();
                    Glide.with(context)
                            .load(new File(selected.getPath()))
                            .apply(options)
                            .into(imageView);
                })
                .start();
    }
}
