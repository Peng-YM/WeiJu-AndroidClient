package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.view.base.BaseActivity;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskActivity;
import io.reactivex.disposables.CompositeDisposable;

import static cn.edu.sustc.androidclient.model.data.Transaction.TransactionStatus.FINISHED;
import static cn.edu.sustc.androidclient.model.data.Transaction.TransactionStatus.PROGRESSING;

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
        // TODO: error encoding
        // show task description in web view
        binding.taskDescriptions
                .loadDataWithBaseURL(null, task.description,
                        "text/html; charset=utf-8", "UTF-8", null);
        setButton();
    }

    private void setButton(){
        // if task is not finished, enter task page
        Transaction transaction = viewModel.hasUnfinishedTransaction();
        if (transaction != null && transaction.status == PROGRESSING){
            binding.takeTaskBtn.setText(getString(R.string.enter_task));
            binding.takeTaskBtn.setOnClickListener(view -> {
                switch (task.type){
                    case Task.TaskType.ANNOTATION:
                        break;
                    case Task.TaskType.COLLECTION:
                        CollectionTaskActivity.start(this, task, transaction);
                        break;
                }
            });
        }
        else if(transaction != null && transaction.status == FINISHED){
            binding.takeTaskBtn.setEnabled(false);
            binding.takeTaskBtn.setText(getString(R.string.finished_task));
        }
        else {
            binding.takeTaskBtn.setOnClickListener(view -> {
                viewModel.applyTask().observe(this, resource -> {
                    showLoading();
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            hideLoading();
                            showAlertDialog(getString(R.string.info), getString(R.string.apply_success));
                            setButton();
                            break;
                        case ERROR:
                            hideLoading();
                            showAlertDialog(getString(R.string.error), resource.message);
                            break;
                    }
                });
            });
        }
    }

    private void enterTask(){

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
