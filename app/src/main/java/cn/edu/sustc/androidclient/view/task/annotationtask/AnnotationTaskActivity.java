package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.UUID;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.Status;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    private AnnotationTaskViewModel viewModel;
    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;

    private int startX=0, startY=0, endX=0, endY=0;

    public static void start(Context context){
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
        annotateImageView.setOnTouchListener((view, motionEvent) -> onAnnotationTouched(motionEvent));

        binding.downloadButton.setOnClickListener(view -> {
            downloadBtnClicked();
        });
        binding.undoButton.setOnClickListener(view -> {
            annotateImageView.undo();
        });
        binding.clearButton.setOnClickListener(view -> {
            annotateImageView.clear();
        });
    }

    private void downloadBtnClicked(){
        String url = binding.pictureUrl.getText().toString();
        String path = this.getFilesDir().getPath() + "/" + UUID.randomUUID() + ".jpg";
        viewModel.downloadFile(url, path).observe(this, fileMyResource -> {
           if (fileMyResource != null){
               switch (fileMyResource.status){
                   case SUCCESS:
                       File downloadedFile = fileMyResource.data;
                       Logger.d("Successfully downloaded file: %s", downloadedFile.getAbsolutePath());
                       break;
                   case ERROR:
                       Toast.makeText(this,"Cannot download file " + url, Toast.LENGTH_LONG).show();
                       break;
               }

           }
        });
    }

    private boolean onAnnotationTouched(MotionEvent event){
        AnnotateImageView.EditMode mode = annotateImageView.getMode();
        switch (mode){
            case EDIT:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        return true;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        endX = (int) event.getX();
                        endY = (int) event.getY();
                        Shape currentShape = new Rectangle(startX, startY, endX, endY);
                        annotateImageView.addDraftShape(currentShape);
                        annotateImageView.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        endX = (int) event.getX();
                        endY = (int) event.getY();
                        // ignore the rectangle that is too small(created from user's click, not drag)
                        if (endX - startX > 10) {
                            Shape currentShape = new Rectangle(startX, startY, endX, endY);
                            annotateImageView.addShape(currentShape);
                            annotateImageView.invalidate();
                            return true;
                        }
                        return true;
                    }
                }
            case SELECT:
                return true;
            default:
                return true;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_annotation_task;
    }
}
