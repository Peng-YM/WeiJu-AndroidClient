package cn.edu.sustc.androidclient.view.task.publishtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Date;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskPublishBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class TaskPublishActivity extends BaseActivity<TaskPublishViewModel, ActivityTaskPublishBinding> {
    @Inject
    TaskPublishViewModel viewModel;
    private ActivityTaskPublishBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, TaskPublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        binding.editorButton.setOnClickListener(view -> {
            RichEditorActivity.start(this);
        });
        binding.publishButton.setOnClickListener(view -> {
            publishTask();
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_publish;
    }

    private void publishTask(){
        Task task = new Task();
        // TODO: upload cover here
        task.cover = "";
        task.name = binding.taskName.getText().toString();
        // TODO: get description from activity
        task.description = "";
        // TODO: task type
        task.type = 0;
        task.start = String.valueOf(new Date().getTime());
        // TODO: task end
        task.end = String.valueOf(new Date().getTime());
        viewModel.publishTask(task).observe(this, resource->{
            showLoading();
            switch (resource.status){
                case SUCCESS:
                    hideLoading();
                    showAlertDialog(getString(R.string.info), getString(R.string.publish_success));
                    finish();
                    break;
                case ERROR:
                    hideLoading();
                    showAlertDialog(getString(R.string.error), getString(R.string.publish_error));
                    break;
                case LOADING:
                    break;
            }
        });
    }
}
