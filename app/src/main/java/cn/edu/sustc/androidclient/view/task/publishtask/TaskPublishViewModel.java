package cn.edu.sustc.androidclient.view.task.publishtask;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

public class TaskPublishViewModel extends BaseViewModel {
    private TaskRepository taskRepository;

    @Inject
    public TaskPublishViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public MutableLiveData<MyResource<Task>> publishTask(Task task){
        return taskRepository.publishTask(task);
    }
}
