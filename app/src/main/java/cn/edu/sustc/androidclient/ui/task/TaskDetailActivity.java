package cn.edu.sustc.androidclient.ui.task;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.zzhoujay.richtext.RichText;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.Task;
import cn.edu.sustc.androidclient.ui.main.TaskViewModel;

public class TaskDetailActivity extends BaseActivity {
    private ActivityTaskDetailBinding binding;
    public static void start(Context context, Task task){
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail);
        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("task");
        TaskViewModel viewModel = new TaskViewModel(task);
        binding.setViewModel(viewModel);
        RichText.fromMarkdown(task.descriptions).into(binding.taskMarkdownDescriptions);
    }
}
