package cn.edu.sustc.androidclient.view.task.publishtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskPublishBinding;
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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_publish;
    }
}
