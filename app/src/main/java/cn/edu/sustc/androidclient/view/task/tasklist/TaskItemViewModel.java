package cn.edu.sustc.androidclient.view.task.tasklist;

import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailActivity;

import static cn.edu.sustc.androidclient.model.data.Task.TaskType.ANNOTATION;
import static cn.edu.sustc.androidclient.model.data.Task.TaskType.COLLECTION;

public class TaskItemViewModel extends BaseViewModel {
    public Task task;

    public TaskItemViewModel(Task task) {
        this.task = task;
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
}
