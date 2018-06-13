package cn.edu.sustc.androidclient.view.task.publishtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskPublishBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class TaskPublishActivity extends BaseActivity<TaskPublishViewModel, ActivityTaskPublishBinding> {
    @Inject
    TaskPublishViewModel viewModel;
    @Inject
    AwesomeValidation validation;

    private ActivityTaskPublishBinding binding;
    private Task task;

    public static void start(Context context) {
        Intent intent = new Intent(context, TaskPublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        this.task = new Task();
        setValidation();
        setWidget();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_publish;
    }

    private void setValidation() {
        validation.addValidation(binding.taskName, s -> s.trim().length() != 0,
                getString(R.string.alert_field_empty));
        validation.addValidation(binding.taskSize, s -> s.trim().length() != 0,
                getString(R.string.alert_field_empty));
        validation.addValidation(binding.taskSize, Pattern.compile("^[0-9]*$"),
                getString(R.string.alert_field_number));
    }

    private void setWidget() {
        binding.editorButton.setOnClickListener(view -> RichEditorActivity.start(this));
        binding.publishButton.setOnClickListener(view -> publishTask());
        binding.taskCover.setOnClickListener(view -> Album.image(this)
                .singleChoice()
                .camera(true)
                .onResult((requestCode, result) -> {
                    // show selected image
                    AlbumFile selected = result.get(0);
                    RequestOptions options = new RequestOptions()
                            .centerCrop();
                    Glide.with(this)
                            .load(new File(selected.getPath()))
                            .apply(options)
                            .into(binding.taskCover);
                    // upload picture to server
                    viewModel.uploadCover(selected.getPath()).observe(this, resource -> {
                        switch (resource.status) {
                            case SUCCESS:
                                task.cover = resource.data;
                                break;
                            case LOADING:
                                break;
                            case ERROR:
                                showAlertDialog(getString(R.string.error), getString(R.string.upload_failed));
                        }
                    });
                }).start());
    }

    private void publishTask(){
        if (validation.validate()) {
            task.name = binding.taskName.getText().toString();
            // TODO: get description from activity
            task.description = "";
            // TODO: task type
            task.type = 0;
            task.start = String.valueOf(new Date().getTime());
            // TODO: task end
            task.end = String.valueOf(new Date().getTime());
            viewModel.publishTask(task).observe(this, resource -> {
                showLoading();
                switch (resource.status) {
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
}
