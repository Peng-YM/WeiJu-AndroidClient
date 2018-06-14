package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.rx.AppSchedulerProvider;
import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.MyRequest;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.AnnotationCommits;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Task.AnnotationTaskFormatter;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import cn.edu.sustc.androidclient.model.service.TaskService;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static cn.edu.sustc.androidclient.model.data.Transaction.TransactionStatus.FINISHED;

public class TaskRepository{
    // injected module
    private SharedPreferences preferences;
    private TaskService taskService;
    private AppSchedulerProvider schedulerProvider;
    private MyDataBase dataBase;
    private Context context;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public TaskRepository(SharedPreferences preferences,
            TaskService taskService, AppSchedulerProvider schedulerProvider,
            MyDataBase dataBase, Context context) {
        this.preferences = preferences;
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

    public LiveData<MyResource<Transaction>> applyTask(TransactionInfo info) {
        MutableLiveData<MyResource<Transaction>> transaction = new MutableLiveData<>();
        MyResource<Transaction> resource = MyResource.loading(null);
        transaction.postValue(resource);
        taskService.applyTask(new MyRequest<>(info))
                .subscribeOn(schedulerProvider.newThread())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<MyResponse<Transaction>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(MyResponse<Transaction> response) {
                        response.data.userId = info.userId;
                        MyResource<Transaction> resource = MyResource.success(response.data);
                        transaction.postValue(resource);
                        dataBase.transactionDao()
                                .addTransaction(response.data);
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

    public Transaction hasUnfinishedTransaction(int taskId){
        int userId = preferences.getInt("id", 0);
        return dataBase.transactionDao().findUnfinishedTask(userId, taskId);
    }

    public void finishTransaction(int transactionId) {
        Transaction transaction = dataBase.transactionDao().findById(transactionId);
        transaction.status = FINISHED;
        dataBase.transactionDao().updateTransaction(transaction);
    }

    public LiveData<MyResource<Task>> publishTask(Task task) {
        MutableLiveData<MyResource<Task>> resource = new MutableLiveData<>();
        resource.postValue(MyResource.loading(null));
        taskService.createTask(new MyRequest<>(task))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<MyResponse<Task>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(MyResponse<Task> response) {
                        resource.postValue(MyResource.success(response.data));
                    }

                    @Override
                    public void onError(Throwable e) {
                        resource.postValue(MyResource.error(e.getMessage(), null));
                    }
                });
        return resource;
    }

    public LiveData<MyResource<AnnotationTaskFormatter>> getAnnotationTaskFormatter(int taskId){
        MutableLiveData<MyResource<AnnotationTaskFormatter>> resource =
                new MutableLiveData<>();
        resource.postValue(MyResource.loading(null));
        taskService.getAnnotationTaskFormatter(taskId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<AnnotationTaskFormatter>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(AnnotationTaskFormatter annotationTaskFormatter) {
                        resource.postValue(MyResource.success(annotationTaskFormatter));
                    }

                    @Override
                    public void onError(Throwable e) {
                        resource.postValue(MyResource.error(e.getMessage(), null));
                    }
                });
        return resource;
    }

    public LiveData<MyResource> annotationCommit(AnnotationCommits commits){
        MutableLiveData<MyResource> resource =
                new MutableLiveData<>();
        resource.postValue(MyResource.loading(null));
        taskService.annotationCommit(new MyRequest(commits))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposables.add(d);
                            }

                            @Override
                            public void onComplete() {
                                resource.postValue(MyResource.success(null));
                            }

                            @Override
                            public void onError(Throwable e) {
                                resource.postValue(MyResource.error(e.getMessage(), null));
                            }
                        }
                );
        return resource;
    }
}
