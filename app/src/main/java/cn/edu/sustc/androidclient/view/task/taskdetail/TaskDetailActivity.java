package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseActivity;
import cn.edu.sustc.androidclient.view.task.TaskViewModel;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TaskDetailActivity extends BaseActivity<TaskViewModel, ActivityTaskDetailBinding> {
    // injected modules
    @Inject
    TaskViewModel viewModel;
    private ActivityTaskDetailBinding binding;

    private Task task;
    public CompositeDisposable disposables;

    public static void start(Context context, Task task) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        this.disposables = new CompositeDisposable();
        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        viewModel.setTask(task);

        binding.setViewModel(viewModel);
        //TODO: change to task description!
        binding.taskDescriptions.loadUrl("file:///android_asset/about.html");
        binding.takeTaskBtn.setOnClickListener(view -> {});
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
