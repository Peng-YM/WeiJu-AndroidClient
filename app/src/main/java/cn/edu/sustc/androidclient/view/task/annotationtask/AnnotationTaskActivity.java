package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    @Inject
    AnnotationTaskViewModel viewModel;

    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;
    private Task task;
    private final List<String> paths = Arrays.asList(
            "/storage/emulated/0/DCIM/Camera/IMG_20180414_030741.jpg",
            "/storage/emulated/0/DCIM/20180422_084623948_0c8b5e93bf380cbdfe07ed7f2d861a08.jpg",
            "/storage/emulated/0/DCIM/20180427_153654166_7f105819762db7895fdc17ce2e9980f3.jpg",
            "/storage/emulated/0/DCIM/20180427_153654166_7f105819762db7895fdc17ce2e9980f3.jpg",
            "/storage/emulated/0/DCIM/Camera/IMG_20180414_030745.jpg"
    );
    private int currentIdx = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, AnnotationTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        binding.addTag.setOnClickListener(v -> popup_tags());
        if (paths.size() > 0){
            annotateImage(currentIdx);
        }
        AnnotateImageView.Mode mode =
                annotateImageView.getMode() == AnnotateImageView.Mode.DRAW
                        ? AnnotateImageView.Mode.ZOOM : AnnotateImageView.Mode.DRAW;
        annotateImageView.setMode(mode);
    }

    private void annotateImage(int index){
        annotateImageView = binding.annotateImageView;
        Bitmap currentBitmap = FileUtils.loadBitmap(paths.get(index));
        annotateImageView.init(currentBitmap);
        annotateImageView.clear();
    }

    private void popup_tags() {
        PopupMenu popup = new PopupMenu(AnnotationTaskActivity.this, binding.addTag);
        Menu menu = popup.getMenu();

        // 此处应该用for循环加载tag
        // 一个粗暴的test
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "tag1");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "tag2");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "tag3");
        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "tag4");
        // menu的item点击事件
        popup.setOnMenuItemClickListener(item -> {
            AnnotateImageView.Mode mode = AnnotateImageView.Mode.DRAW;
            annotateImageView.setMode(mode);

            switch (item.getItemId()) {
                case Menu.FIRST + 0:
                    // 对标签模式的设置（颜色等）
                    break;
                case Menu.FIRST + 1:
                    break;
                case Menu.FIRST + 2:
                    break;
                case Menu.FIRST + 3:
                    break;
            }
            return false;
        });

        popup.show(); //showing popup menu
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_annotation_task;
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.annotation_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.annotation_next:
                Logger.d("Annotate next picture");
                if (currentIdx < paths.size() - 1)
                    annotateImage(++currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_last));
                break;
            case R.id.annotation_prev:
                Logger.d("Annotate previous picture");
                if (currentIdx > 0)
                    annotateImage(--currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_first));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
