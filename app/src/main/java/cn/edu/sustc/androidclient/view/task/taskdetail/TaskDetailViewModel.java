package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

import static cn.edu.sustc.androidclient.model.data.Task.TaskType.ANNOTATION;
import static cn.edu.sustc.androidclient.model.data.Task.TaskType.COLLECTION;

public class TaskDetailViewModel extends BaseViewModel {
    public Task task;
    private TaskRepository taskRepository;
    private SharedPreferences sharedPreferences;

    @Inject
    public TaskDetailViewModel(TaskRepository taskRepository, SharedPreferences sharedPreferences) {
        this.taskRepository = taskRepository;
        this.sharedPreferences = sharedPreferences;
    }
    public void setTask(Task task) {
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

    public LiveData<MyResource<Transaction>> applyTask(){
        int userId = sharedPreferences.getInt("id", 0);
        TransactionInfo info = new TransactionInfo(userId, task.taskId);
        return taskRepository.applyTask(info);
    }
}
