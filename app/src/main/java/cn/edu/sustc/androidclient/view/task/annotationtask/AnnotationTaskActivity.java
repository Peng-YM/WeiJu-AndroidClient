package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_annotation_task;
    }
}
