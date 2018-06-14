package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.arch.lifecycle.LiveData;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Task.AnnotationTaskFormatter;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;

public class AnnotationTaskViewModel extends BaseViewModel {
    private TaskRepository taskRepository;

    public AnnotationTaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public LiveData<MyResource<AnnotationTaskFormatter>> getFormatter(int taskId){
        return taskRepository.getAnnotationTaskFormatter(taskId);
    }

}
