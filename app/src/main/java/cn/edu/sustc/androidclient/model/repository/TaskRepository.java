package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.Status;
import cn.edu.sustc.androidclient.common.base.BaseViewModel;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.service.TaskService;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TaskRepository implements BaseViewModel{
    @Deprecated
    private static TaskRepository instance;
    @Deprecated
    private TaskRepository(TaskService taskService){
        this.taskService = taskService;
    }
    @Deprecated
    public static TaskRepository getInstance(){
        if (instance == null){
            synchronized (UserRepository.class){
                if (instance == null){
                    instance = new TaskRepository(
                            RetrofitFactory.getInstance().create(TaskService.class)
                    );
                }
            }
        }
        return instance;
    }
    @Deprecated
    public void getTasks(Observer<Task> observer){
        // TODO: Paging
        this.taskService
                .fakeGetTasks()
                .map(response -> response.data)
                .flatMap(Observable::fromIterable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     *  New Code
     * */

    // injected module
    private TaskService taskService;
    private AppSchedulerProvider schedulerProvider;

    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<MyResource<List<Task>>> liveTaskList = new MutableLiveData<>();

    @Inject
    public TaskRepository(TaskService taskService, AppSchedulerProvider schedulerProvider){
        this.taskService = taskService;
        this.schedulerProvider = schedulerProvider;
    }

    public MutableLiveData<MyResource<List<Task>>> getTaskList(int offset, int limit){
        // TODO: remove fake method
        this.taskService
                .fakeGetTasks()
//                .getTasks(offset, limit)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe(new Observer<MyResponse<List<Task>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                        MyResource<List<Task>> resource = MyResource.loading(null);
                        liveTaskList.postValue(resource);
                    }

                    @Override
                    public void onNext(MyResponse<List<Task>> response) {
                        MyResource<List<Task>> resource = MyResource.success(response.data);
                        liveTaskList.postValue(resource);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyResource<List<Task>> resource = MyResource.error("Error fetching task!", null);
                        liveTaskList.postValue(resource);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return liveTaskList;
    }


    @Override
    public void onClear() {
        disposables.dispose();
    }
}
