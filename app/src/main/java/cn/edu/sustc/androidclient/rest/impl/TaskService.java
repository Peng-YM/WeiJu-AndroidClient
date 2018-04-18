package cn.edu.sustc.androidclient.rest.impl;

import java.util.List;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Task;
import cn.edu.sustc.androidclient.repository.UserRepository;
import cn.edu.sustc.androidclient.rest.TaskAPI;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

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

    public void getTasks(Observer<Task> observer){
        // TODO: Paging
        this.taskAPI
                .fakeGetTasks()
                .map(response -> response.data)
                .flatMap((Function<List<Task>, Observable<Task>>) Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
