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

    public MutableLiveData<MyResource<Task>> publishTask(Task task){
        return taskRepository.publishTask(task);
    }


    public LiveData<MyResource<String>> uploadCover(String imagePath) {
        MutableLiveData<MyResource<String>> urlLive = new MutableLiveData<>();
        urlLive.postValue(MyResource.loading(null));

        File image = new File(imagePath);
        fileRepository.upload(Constants.FILE_URL, image, new SingleObserver<MyResponse<List<String>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                getCompositeDisposable().add(d);
            }

            @Override
            public void onSuccess(MyResponse<List<String>> response) {
                String firstUrl = response.data.get(0);
                urlLive.postValue(MyResource.success(firstUrl));
            }

            @Override
            public void onError(Throwable e) {
                urlLive.postValue(MyResource.error(e.getMessage(), null));
            }
        });
        return urlLive;
    }
}
