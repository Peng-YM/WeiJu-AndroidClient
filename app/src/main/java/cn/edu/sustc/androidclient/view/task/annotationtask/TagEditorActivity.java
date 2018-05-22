package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTagEditorBinding;

public class TagEditorActivity extends BaseActivity<AnnotationTaskViewModel, ActivityTagEditorBinding> {

    @Override
    protected Class<AnnotationTaskViewModel> getViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, AnnotationTaskViewModel viewModel, ActivityTagEditorBinding binding) {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

}
