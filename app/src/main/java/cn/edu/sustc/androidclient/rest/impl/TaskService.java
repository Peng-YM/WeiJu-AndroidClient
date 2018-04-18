package cn.edu.sustc.androidclient.rest.impl;

import java.util.List;;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Task;
import cn.edu.sustc.androidclient.repository.UserRepository;
import cn.edu.sustc.androidclient.rest.TaskAPI;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class TaskService {
    private static TaskService instance;
    private TaskAPI taskAPI;
    private TaskService(TaskAPI taskAPI){
        this.taskAPI = taskAPI;
    }

    public static TaskService getInstance(){
        if (instance == null){
            synchronized (UserRepository.class){
                if (instance == null){
                    instance = new TaskService(
                            RetrofitFactory.getInstance().create(TaskAPI.class)
                    );
                }
            }
        }
        return instance;
    }

    public void getTasks(Subscriber<Task> subscriber){
        // TODO: Paging
        this.taskAPI
                .fakeGetTasks()
                .map(new Func1<MyResponse<List<Task>>, List<Task>>() {
                    @Override
                    public List<Task> call(MyResponse<List<Task>> response) {
                        return response.data;
                    }
                })
                .flatMap(new Func1<List<Task>, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(List<Task> tasks) {
                        return Observable.from(tasks);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
