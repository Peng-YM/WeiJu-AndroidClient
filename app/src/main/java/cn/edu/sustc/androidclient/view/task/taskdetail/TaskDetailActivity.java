package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseActivity;
import io.reactivex.disposables.CompositeDisposable;

public class TaskDetailActivity extends BaseActivity<TaskDetailViewModel, ActivityTaskDetailBinding> {
    public CompositeDisposable disposables;
    // injected modules
    @Inject
    TaskDetailViewModel viewModel;
    private ActivityTaskDetailBinding binding;
    private Task task;

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
        // show task description in web view
        binding.taskDescriptions
                .loadData(task.description, "text/html; charset=utf-8", "UTF-8");
        binding.takeTaskBtn.setOnClickListener(view -> {

        });
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
