package cn.edu.sustc.androidclient.view.task;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailActivity;

public class TaskViewModel extends ViewModel{
    private Task task;

    @Deprecated
    public TaskViewModel(Task task){
        this.task = task;
    }

    @Inject
    public TaskViewModel(){}

    public String getCoverUrl(){
        // TODO: change base url
        String BASE_URL = RetrofitFactory.getBaseUrl();
        BASE_URL = "http://69.171.71.251:8080/";
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

    public String getDescriptions(){
        return task.descriptions;
    }

    @BindingAdapter({"myApp:imageUrl"})
    public static void loadImage(ImageView imageView, String url){
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

    public void onClick(View view) {
        Logger.json(task.toString());
        TaskDetailActivity.start(view.getContext(), task);
    }


    public void setTask(Task task) {
        this.task = task;
    }
}
