package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.task.TaskViewModel;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TaskDetailActivity extends BaseActivity<TaskViewModel, ActivityTaskDetailBinding> {
    // injected modules
    private ActivityTaskDetailBinding binding;
    private TaskViewModel viewModel;
    private Task task;
    public CompositeDisposable disposables;

    public static void start(Context context, Task task) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }

    @Override
    protected Class<TaskViewModel> getViewModel() {
        return TaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, TaskViewModel viewModel, ActivityTaskDetailBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;
        this.disposables = new CompositeDisposable();
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        viewModel.setTask(task);

        binding.setViewModel(viewModel);
        binding.taskDescriptions.loadData(task.descriptions, "text/html; charset=utf-8", "UTF-8");
        binding.takeTaskBtn.setOnClickListener(view ->
                viewModel.takeTask()
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposables.add(d);
                            }

                            @Override
                            public void onComplete() {
                                Logger.d("Added task: %s", task);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Logger.e("Cannot add task!");
                            }
                        })
        );
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
