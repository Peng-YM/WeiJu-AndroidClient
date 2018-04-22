package cn.edu.sustc.androidclient.view.main.tasklist;

import android.arch.lifecycle.ViewModel;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.BR;
import cn.edu.sustc.androidclient.common.base.CompletedListener;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TaskFragmentViewModel extends ViewModel{
    public ObservableField<Integer> contentViewVisibility;
    public ObservableField<Integer> progressBarVisibility;
    public ObservableField<Integer> errorInfoLayoutVisibility;
    public ObservableField<String> exception;

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
                contentViewVisibility.set(View.VISIBLE);
                completedListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("Unable to get tasks");
                Logger.e(e.getMessage());
                hideAll();
                errorInfoLayoutVisibility.set(View.VISIBLE);
                exception.set(e.getMessage());
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
        contentViewVisibility = new ObservableField<>(View.VISIBLE);
        progressBarVisibility = new ObservableField<>(View.GONE);
        errorInfoLayoutVisibility = new ObservableField<>(View.GONE);
    }

    public void refreshData() {
        getTasks();
    }

    private void hideAll(){
        contentViewVisibility = new ObservableField<>(View.GONE);
        progressBarVisibility = new ObservableField<>(View.GONE);
        errorInfoLayoutVisibility = new ObservableField<>(View.GONE);
    }
}
