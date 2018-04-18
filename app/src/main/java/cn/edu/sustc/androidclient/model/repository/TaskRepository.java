package cn.edu.sustc.androidclient.model.repository;

import java.util.List;

import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.service.TaskService;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

public class TaskRepository {
    private static TaskRepository instance;
    private TaskService taskService;
    private TaskRepository(TaskService taskService){
        this.taskService = taskService;
    }

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

    public void getTasks(Observer<Task> observer){
        // TODO: Paging
        this.taskService
                .fakeGetTasks()
                .map(response -> response.data)
                .flatMap((Function<List<Task>, Observable<Task>>) Observable::fromIterable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
