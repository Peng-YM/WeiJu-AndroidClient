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
import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailActivity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static cn.edu.sustc.androidclient.model.data.Task.TaskType.ANNOTATION;
import static cn.edu.sustc.androidclient.model.data.Task.TaskType.COLLECTION;

public class TaskViewModel extends ViewModel {
    public Task task;
    private MyDataBase dataBase;

    @Deprecated
    public TaskViewModel(Task task) {
        this.task = task;
    }

    public TaskViewModel(MyDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @BindingAdapter({"myApp:imageUrl"})
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

    public String getCoverUrl() {
        // TODO: change base url
        String BASE_URL = "http://69.171.71.251:9000/";
        return BASE_URL + task.cover;
    }

    public String getTaskType() {
        switch (task.type) {
            case ANNOTATION:
                return "标注任务";
            case COLLECTION:
                return "采集任务";
            default:
                return "未知任务";
        }
    }

    public void onClick(View view) {
        Logger.json(task.toString());
        TaskDetailActivity.start(view.getContext(), task);
    }

    public Completable takeTask() {
        return Completable.fromAction(() -> {
            dataBase.taskDao()
                    .saveTask(task);
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
