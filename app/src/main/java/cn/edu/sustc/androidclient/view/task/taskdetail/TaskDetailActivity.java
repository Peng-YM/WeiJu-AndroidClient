package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzhoujay.richtext.RichText;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.task.TaskViewModel;
import cn.edu.sustc.androidclient.view.task.annotationtask.AnnotationTaskActivity;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskActivity;

public class TaskDetailActivity extends BaseActivity<TaskViewModel, ActivityTaskDetailBinding> {
    // injected modules
    private ActivityTaskDetailBinding binding;
    private TaskViewModel viewModel;
    private Task task;

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

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        viewModel.setTask(task);

        binding.setViewModel(viewModel);
        RichText.fromMarkdown(task.descriptions).into(binding.taskMarkdownDescriptions);
        binding.takeTaskBtn.setOnClickListener(view -> takeTask());
    }

    private void takeTask(){
        switch (task.type){
            case COLLECTION:
                //TODO: pass task to intent
                CollectionTaskActivity.start(this, task);
                break;
            case ANNOTATION:
                //TODO: pass task to intent
                AnnotationTaskActivity.start(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_detail;
    }
}
