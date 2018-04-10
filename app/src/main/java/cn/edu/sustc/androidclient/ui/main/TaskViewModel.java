package cn.edu.sustc.androidclient.ui.main;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.model.Task;

public class TaskViewModel extends BaseObservable{
    private Task task;

    public TaskViewModel(Task task){
        this.task = task;
    }

    public String getCoverUrl(){
        // TODO: remove dummy
//        if(task.pictures.isEmpty()){
//            return "";
//        }
//        // get first picture as cover
//        return task.pictures.get(0);
        return "https://en.wikipedia.org/wiki/File:Lenna.png";
    }

    public String getTitle(){
        return task.title;
    }

    public String getAuthor(){
        return task.author;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.ic_load_error);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
