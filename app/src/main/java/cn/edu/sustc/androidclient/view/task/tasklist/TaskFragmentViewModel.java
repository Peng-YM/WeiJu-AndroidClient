package cn.edu.sustc.androidclient.view.task.tasklist;

import android.arch.lifecycle.MutableLiveData;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.edu.sustc.androidclient.common.ListFilter;
import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaskFragmentViewModel extends BaseViewModel {
    private TaskRepository taskRepository;
    private ListFilter<Task> taskListFilter;

    public TaskFragmentViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public MutableLiveData<MyResource> getTasks(TaskAdapter adapter) {
        MutableLiveData<MyResource> finished = new MutableLiveData<>();
        finished.postValue(MyResource.loading(null));
        taskRepository.getTaskList(0, 20).subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                getCompositeDisposable().add(d);
            }

            @Override
            public void onNext(Task task) {
                if (taskListFilter == null){
                    adapter.addItem(task);
                }
                else if (taskListFilter.filter(task)) {
                    adapter.addItem(task);
                }
            }

            @Override
            public void onError(Throwable e) {
                finished.postValue(MyResource.error(e.getMessage(), null));
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                finished.postValue(MyResource.success(null));
            }
        });
        return finished;
    }

    public void setStatusFilter(int statusFilter) {
        Logger.d("Filter is set!");
        this.taskListFilter = (task) -> {
            Transaction transaction = taskRepository.hasUnfinishedTransaction(task.taskId);
            Logger.d("Filtering task" + task.toString());
            // show all task
            if (transaction != null && statusFilter == -1) {
                Logger.d("Passed 1");
                return true;
            }
            else {
                Logger.d("Passed 2");
                return transaction != null && statusFilter == transaction.status;
            }
        };
    }
}
