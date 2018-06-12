package cn.edu.sustc.androidclient.view.task.taskmanager;

import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

public class TaskManagerViewModel extends BaseViewModel {
    private TaskRepository taskRepository;

    public TaskManagerViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
