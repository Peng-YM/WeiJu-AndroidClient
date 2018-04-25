package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.os.Bundle;

import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTagEditorBinding;

public class TagEditorActivity extends BaseActivity<AnnotationTaskViewModel, ActivityTagEditorBinding> {
    @Override
    protected Class<AnnotationTaskViewModel> getViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle instance, AnnotationTaskViewModel viewModel, ActivityTagEditorBinding binding) {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
