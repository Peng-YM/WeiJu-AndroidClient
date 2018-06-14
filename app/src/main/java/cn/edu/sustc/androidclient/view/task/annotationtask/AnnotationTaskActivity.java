package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    @Inject
    AnnotationTaskViewModel viewModel;

    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;

    private Transaction transaction;
    private Task task;

    private int currentIdx = 0;

    public static void start(Context context, Task task, Transaction transaction) {
        Intent intent = new Intent(context, AnnotationTaskActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("transaction", transaction);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setData();
        setView();
    }

    public void setData(){
        Intent intent = getIntent();
        this.transaction = (Transaction) intent.getSerializableExtra("transaction");
        this.task = (Task) intent.getSerializableExtra("task");
        Logger.d(transaction.pictures);
    }

    public void setView(){
        binding.addTag.setOnClickListener(v -> popup_tags());
        if (transaction.pictures.size() > 0){
            annotateImage(currentIdx);
        }
        AnnotateImageView.Mode mode =
                annotateImageView.getMode() == AnnotateImageView.Mode.DRAW
                        ? AnnotateImageView.Mode.ZOOM : AnnotateImageView.Mode.DRAW;
        annotateImageView.setMode(mode);
    }

    private void annotateImage(int index){
        annotateImageView = binding.annotateImageView;
        showLoading();
        Glide.with(this)
                .asBitmap()
                .load(transaction.pictures.get(index))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        hideLoading();
                        annotateImageView.init(resource);
                        annotateImageView.clear();
                    }
                });
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
                Logger.v("Annotate next picture");
                if (currentIdx < transaction.pictures.size() - 1)
                    annotateImage(++currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_last));
                break;
            case R.id.annotation_prev:
                Logger.v("Annotate previous picture");
                if (currentIdx > 0)
                    annotateImage(--currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_first));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
