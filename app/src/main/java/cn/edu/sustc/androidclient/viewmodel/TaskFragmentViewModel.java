package cn.edu.sustc.androidclient.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.BR;
import cn.edu.sustc.androidclient.common.CompletedListener;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.adapter.TaskAdapter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaskFragmentViewModel extends BaseObservable{
    private int contentViewVisibility;
    private int progressBarVisibility;
    private int errorInfoLayoutVisibility;
    private String exception;

    private TaskAdapter taskAdapter;
    private CompletedListener completedListener;

    public TaskFragmentViewModel(TaskAdapter adapter, CompletedListener completedListener){
        this.taskAdapter = adapter;
        this.completedListener = completedListener;
        initData();
        getTasks();
    }

    private void getTasks(){
        Observer<Task> observer = new Observer<Task>() {
            @Override
            public void onComplete() {
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
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                taskAdapter.addItem(task);
            }
        };

        TaskRepository.getInstance().getTasks(observer);
    }

    private void initData(){
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
