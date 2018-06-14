package cn.edu.sustc.androidclient.view.task.publishtask;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.Constants;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.repository.FileRepository;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class TaskPublishViewModel extends BaseViewModel {
    private TaskRepository taskRepository;
    private FileRepository fileRepository;

    @Inject
    public TaskPublishViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
    }

    public LiveData<MyResource<Task>> publishTask(Task task) {
        return taskRepository.publishTask(task);
    }

    public LiveData<MyResource<String>> uploadCover(String imagePath) {
        return fileRepository.uploadCover(imagePath);
    }
}
