package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.Status;
import cn.edu.sustc.androidclient.common.base.BaseViewModel;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.service.TaskService;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskRepository implements BaseViewModel {
    @Deprecated
    private static TaskRepository instance;
    /**
     * New Code
     */

    // injected module
    private TaskService taskService;
    private AppSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables = new CompositeDisposable();
    private MyResource<List<Task>> taskList;
    private MutableLiveData<MyResource<List<Task>>> liveTaskList;

    @Deprecated
    private TaskRepository(TaskService taskService) {
        this.taskService = taskService;
    }

    @Inject
    public TaskRepository(TaskService taskService, AppSchedulerProvider schedulerProvider) {
        this.taskService = taskService;
        this.schedulerProvider = schedulerProvider;
    }

    @Deprecated
    public static TaskRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new TaskRepository(
                            RetrofitFactory.getInstance().create(TaskService.class)
                    );
                }
            }
        }
        return instance;
    }

    @Deprecated
    public void getTasks(Observer<Task> observer) {
        // TODO: Paging
        this.taskService
                .fakeGetTasks()
                .map(response -> response.data)
                .flatMap(Observable::fromIterable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public MutableLiveData<MyResource<List<Task>>> getTaskList(int offset, int limit) {
        taskList = MyResource.loading(new ArrayList<Task>());
        liveTaskList = new MutableLiveData<>();
        liveTaskList.postValue(taskList);
        // TODO: remove fake method
        this.taskService
                .fakeGetTasks()
//                .getTasks(offset, limit)
                .map(response -> response.data)
                .flatMap(Observable::fromIterable)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new Observer<Task>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Task task) {
                        taskList.data.add(task);
                        liveTaskList.postValue(taskList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        taskList.status = Status.ERROR;
                        taskList.message = "Error loading data";
                        liveTaskList.postValue(taskList);
                    }

                    @Override
                    public void onComplete() {
                        taskList.status = Status.SUCCESS;
                        liveTaskList.postValue(taskList);
                    }
                });
        return liveTaskList;
    }


    @Override
    public void onClear() {
        disposables.dispose();
    }
}
