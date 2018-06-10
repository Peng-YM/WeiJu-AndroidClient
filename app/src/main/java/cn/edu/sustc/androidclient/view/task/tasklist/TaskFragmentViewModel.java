package cn.edu.sustc.androidclient.view.task.tasklist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TaskFragmentViewModel extends ViewModel {
    private TaskRepository taskRepository;
    private CompositeDisposable disposables;

    public TaskFragmentViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.disposables = new CompositeDisposable();
    }

    public MutableLiveData<MyResource> getTasks(TaskAdapter adapter) {
        MutableLiveData<MyResource> finished = new MutableLiveData<>();
        finished.postValue(MyResource.loading(null));
        taskRepository.getTaskList(0, 20).subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(Task task) {
                adapter.addItem(task);
            }

            @Override
            public void onError(Throwable e) {
                finished.postValue(MyResource.error(e.getMessage(), null));
            }

            @Override
            public void onComplete() {
                finished.postValue(MyResource.success(null));
            }
        });
        return finished;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }
}
