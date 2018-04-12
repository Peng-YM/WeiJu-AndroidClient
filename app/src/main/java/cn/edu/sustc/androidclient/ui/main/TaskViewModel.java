package cn.edu.sustc.androidclient.ui.main;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;


import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Task;
import cn.edu.sustc.androidclient.ui.task.TaskDetailActivity;

public class TaskViewModel extends BaseObservable{
    private Task task;

    public TaskViewModel(Task task){
        this.task = task;
    }

    public String getCoverUrl(){
        // TODO: change base url
        String BASE_URL = RetrofitFactory.getBaseUrl();
        BASE_URL = "http://10.0.2.2:8080/";
        if (task.pictures != null && !task.pictures.isEmpty()){
            return BASE_URL + task.pictures.get(0);
        }
        return "";
    }

    public String getTitle(){
        return task.title;
    }

    public String getAuthor(){
        return task.author;
    }

    @BindingAdapter({"myApp:imageUrl"})
    public static void loadImage(ImageView imageView, String url){
        Logger.d("Fetch Image from url: %s", url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.logo)
                .error(R.drawable.ic_load_error);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public void onClick(View view) {
        Logger.json(task.toString());
        TaskDetailActivity.start(view.getContext(), task);
    }
}
