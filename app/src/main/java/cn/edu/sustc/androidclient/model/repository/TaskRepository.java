package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.rx.AppSchedulerProvider;
import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.MyRequest;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import cn.edu.sustc.androidclient.model.service.TaskService;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskRepository{
    // injected module
    private TaskService taskService;
    private AppSchedulerProvider schedulerProvider;
    private MyDataBase dataBase;
    private Context context;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public TaskRepository(TaskService taskService, AppSchedulerProvider schedulerProvider, MyDataBase dataBase, Context context) {
        this.taskService = taskService;
        this.schedulerProvider = schedulerProvider;
        this.dataBase = dataBase;
        this.context = context;
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
        MyResource<Transaction> resource = MyResource.loading(null);
        transaction.postValue(resource);
        taskService.applyTask(new MyRequest<>(info))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<MyResponse<Transaction>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(MyResponse<Transaction> response) {
                        MyResource<Transaction> resource = MyResource.success(response.data);
                        transaction.postValue(resource);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyResource<Transaction> resource =
                                MyResource.error(context.getString(R.string.apply_error), null);
                        Logger.e(e.getMessage());
                        transaction.postValue(resource);
                    }
                });
        return transaction;
    }

    // save transaction into database
    public LiveData<MyResource> saveTransaction(Transaction transaction){
        return null;
    }
}
