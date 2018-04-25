package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.UUID;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    private AnnotationTaskViewModel viewModel;
    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;

    public static void start(Context context) {
        Intent intent = new Intent(context, AnnotationTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<AnnotationTaskViewModel> getViewModel() {
        return AnnotationTaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, AnnotationTaskViewModel viewModel, ActivityAnnotationTaskBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;

        annotateImageView = binding.annotateImageView;
        annotateImageView.init(BitmapFactory.decodeResource(getResources(), R.drawable.cover));
        binding.downloadButton.setOnClickListener(view -> {
            downloadBtnClicked();
        });
        binding.undoButton.setOnClickListener(view -> {
            annotateImageView.undo();
        });
        binding.clearButton.setOnClickListener(view -> {
            annotateImageView.clear();
        });
        binding.modeButton.setOnClickListener(view -> {
            AnnotateImageView.Mode mode =
                    annotateImageView.getMode() == AnnotateImageView.Mode.EDIT
                            ? AnnotateImageView.Mode.SELECT : AnnotateImageView.Mode.EDIT;
            annotateImageView.setMode(mode);
        });
    }

    private void downloadBtnClicked() {
        String url = binding.pictureUrl.getText().toString();
        String path = this.getFilesDir().getPath() + "/" + UUID.randomUUID() + ".jpg";
        viewModel.downloadFile(url, path).observe(this, fileMyResource -> {
            if (fileMyResource != null) {
                switch (fileMyResource.status) {
                    case SUCCESS:
                        File downloadedFile = fileMyResource.data;
                        Logger.d("Successfully downloaded file: %s", downloadedFile.getAbsolutePath());
                        break;
                    case ERROR:
                        Toast.makeText(this, "Cannot download file " + url, Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_annotation_task;
    }
}
