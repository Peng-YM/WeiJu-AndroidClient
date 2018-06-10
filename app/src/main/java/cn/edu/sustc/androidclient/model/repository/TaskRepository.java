package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import cn.edu.sustc.androidclient.model.service.TaskService;
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskRepository implements BaseViewModel {
    // injected module
    private TaskService taskService;
    private AppSchedulerProvider schedulerProvider;
    private MyDataBase dataBase;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public TaskRepository(TaskService taskService, AppSchedulerProvider schedulerProvider) {
        this.taskService = taskService;
        this.schedulerProvider = schedulerProvider;
    }

    public Observable<Task> getTaskList(int offset, int limit) {
        return taskService
                .getTasks(offset, limit)
                .map(response -> response.data)
                .flatMap(Observable::fromIterable)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io());
    }

    public MutableLiveData<MyResource<Transaction>> applyTask(TransactionInfo info) {
        MutableLiveData<MyResource<Transaction>> transaction = new MutableLiveData<>();
        taskService.applyTask(info)
                .subscribeOn(Schedulers.newThread())
                .map(response -> {
                    dataBase.transactionDao().addTransaction(response.data);
                    return response;
                })
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<MyResponse<Transaction>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                        MyResource<Transaction> resource = MyResource.loading(null);
                        transaction.postValue(resource);
                    }

                    @Override
                    public void onSuccess(MyResponse<Transaction> response) {
                        MyResource<Transaction> resource = MyResource.success(response.data);
                        transaction.postValue(resource);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyResource<Transaction> resource = MyResource.error("Cannot apply task!", null);
                        transaction.postValue(resource);
                    }
                });
        return transaction;
    }

    @Override
    public void onClear() {
        disposables.dispose();
    }
}
