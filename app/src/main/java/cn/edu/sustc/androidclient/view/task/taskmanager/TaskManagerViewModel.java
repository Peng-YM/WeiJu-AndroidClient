package cn.edu.sustc.androidclient.view.task.taskmanager;

import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

public class TaskManagerViewModel extends BaseViewModel {
    private MyDataBase dataBase;
    private TaskRepository taskRepository;

    public TaskManagerViewModel(MyDataBase dataBase, TaskRepository taskRepository) {
        this.dataBase = dataBase;
        this.taskRepository = taskRepository;
    }
}
