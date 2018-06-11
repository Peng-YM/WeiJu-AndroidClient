package cn.edu.sustc.androidclient.view.task.taskdetail;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

import static cn.edu.sustc.androidclient.model.data.Task.TaskType.ANNOTATION;
import static cn.edu.sustc.androidclient.model.data.Task.TaskType.COLLECTION;

public class TaskDetailViewModel extends BaseViewModel {
    public Task task;
    private MyDataBase dataBase;

    @Inject
    public TaskDetailViewModel(MyDataBase dataBase) {
        this.dataBase = dataBase;
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

    public void setTask(Task task) {
        this.task = task;
    }
}
