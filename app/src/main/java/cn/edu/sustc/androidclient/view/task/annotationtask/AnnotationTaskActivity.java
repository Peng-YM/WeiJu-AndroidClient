package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        binding.downloadButton.setOnClickListener(view -> {
            downloadBtnClicked();
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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_annotation_task;
    }
}
