package cn.edu.sustc.androidclient.ui.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.function.Consumer;

import cn.edu.sustc.androidclient.BR;
import cn.edu.sustc.androidclient.common.CompletedListener;
import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.model.Task;
import cn.edu.sustc.androidclient.rest.TaskService;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TaskFragmentViewModel extends BaseObservable{
    private int contentViewVisibility;
    private int progressBarVisibility;
    private int errorInfoLayoutVisibility;
    private String exception;

    private Subscriber<Task> subscriber;
    private TaskAdapter taskAdapter;
    private CompletedListener completedListener;
    private Retrofit retrofit;

    public TaskFragmentViewModel(TaskAdapter adapter, CompletedListener completedListener){
        this.taskAdapter = adapter;
        this.completedListener = completedListener;
        initData();
        getTasks();
    }

    private void getTasks(){
        subscriber = new Subscriber<Task>() {
            @Override
            public void onCompleted() {
                Logger.v("Successfully Fetched Tasks");
                hideAll();
                setContentViewVisibility(View.VISIBLE);
                completedListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("Unable to get tasks");
                Logger.e(e.getMessage());
                hideAll();
                setErrorInfoLayoutVisibility(View.VISIBLE);
                setException(e.getMessage());
            }

            @Override
            public void onNext(Task task) {
                taskAdapter.addItem(task);
            }
        };

        TaskService taskService = retrofit.create(TaskService.class);
        taskService.fakeGetTasks()
                .map(new Func1<MyResponse<List<Task>>, List<Task>>() {
                    @Override
                    public List<Task> call(MyResponse<List<Task>> listMyResponse) {
                        return listMyResponse.data;
                    }
                })
                .flatMap(new Func1<List<Task>, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(List<Task> tasks) {
                        return Observable.from(tasks);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void initData(){
        retrofit = RetrofitFactory.getInstance();

        setContentViewVisibility(View.VISIBLE);
        setProgressBarVisibility(View.VISIBLE);
        setErrorInfoLayoutVisibility(View.GONE);
    }

    public void refreshData() {
        getTasks();
    }

    private void hideAll(){
        setContentViewVisibility(View.GONE);
        setProgressBarVisibility(View.GONE);
        setErrorInfoLayoutVisibility(View.GONE);
    }

    @Bindable
    public int getContentViewVisibility() {
        return contentViewVisibility;
    }

    public void setContentViewVisibility(int contentViewVisibility) {
        this.contentViewVisibility = contentViewVisibility;
        notifyPropertyChanged(BR.contentViewVisibility);
    }

    @Bindable
    public int getProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(int progressBarVisibility) {
        this.progressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }
    @Bindable
    public int getErrorInfoLayoutVisibility() {
        return errorInfoLayoutVisibility;
    }

    public void setErrorInfoLayoutVisibility(int errorInfoLayoutVisibility) {
        this.errorInfoLayoutVisibility = errorInfoLayoutVisibility;
        notifyPropertyChanged(BR.errorInfoLayoutVisibility);
    }
    @Bindable
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
        notifyPropertyChanged(BR.exception);
    }
}
