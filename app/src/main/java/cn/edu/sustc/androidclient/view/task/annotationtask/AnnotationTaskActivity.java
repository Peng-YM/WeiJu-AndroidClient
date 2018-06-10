package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;
import cn.edu.sustc.androidclient.model.data.AnnotationCommits;
import cn.edu.sustc.androidclient.model.data.Task;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    private AnnotationTaskViewModel viewModel;
    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;
    private Task task;
    private List<Bitmap> pictures;

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
        task = (Task) getIntent().getSerializableExtra("task");

        setTitle(task.title);
        annotateImageView = binding.annotateImageView;
        annotateImageView.init(pictures.get(0));

        binding.addTag.setOnClickListener(v -> popup_tags());
        //动态生成的表单内容
        // TODO: remove this
        String JSONString = FileUtils.readAssetFile(this, "annotationTag.json");

        Logger.json(JSONString);
        binding.nextStep.setOnClickListener(v -> {
            // next image
            // TODO: only for test
            try {
                AnnotationCommits.AnnotationTag tag = new Gson().fromJson(JSONString, AnnotationCommits.AnnotationTag.class);
                TagEditorActivity.start(this, tag);
            }catch (Exception e){
                Logger.e("Cannot interpret JSON");
                e.printStackTrace();
            }
        });

//        binding.undoButton.setOnClickListener(view -> {
//            annotateImageView.undo();
//        });
//        binding.clearButton.setOnClickListener(view -> {
//            annotateImageView.clear();
//        });
//        binding.modeButton.setOnClickListener(view -> {
            AnnotateImageView.Mode mode =
                    annotateImageView.getMode() == AnnotateImageView.Mode.EDIT
                            ? AnnotateImageView.Mode.SELECT : AnnotateImageView.Mode.EDIT;
            annotateImageView.setMode(mode);
//        });

    }

    private void popup_tags(){
        PopupMenu popup = new PopupMenu(AnnotationTaskActivity.this, binding.addTag);
        Menu menu = popup.getMenu();

        // 此处应该用for循环加载tag
        // 一个粗暴的test
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "tag1");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "tag2");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "tag3");
        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "tag4");
        // menu的item点击事件
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AnnotateImageView.Mode mode = AnnotateImageView.Mode.EDIT;
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
            }
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

        if (id == R.id.annotation_undo) {
            annotateImageView.undo();
        }
        return super.onOptionsItemSelected(item);
    }
}
