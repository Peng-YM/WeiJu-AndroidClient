package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.Arrays;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.utils.FileUtils;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;
import cn.edu.sustc.androidclient.model.data.AnnotationCommits.AnnotationTag;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    private static final int CODE = 1;

    @Inject
    AnnotationTaskViewModel viewModel;

    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;

    private Transaction transaction;
    private Task task;
    private MaterialDialog tagDialog;
    private AnnotationTag tag;

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
        setTagDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE){
            AnnotationTag resultTag = (AnnotationTag) data.getSerializableExtra("tag");
            // TODO: save result
        }
    }

    private void setTagDialog(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(R.layout.dialog_custom, null);
        LinearLayout tagLayout = stdView.findViewById(R.id.tag_dialog_layout);
        Spinner spinner = tagLayout.findViewById(R.id.tag_dialog_spinner);
        // TODO: filled with tag list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Arrays.asList(getString(R.string.collection_task), getString(R.string.annotation_task)));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        this.tagDialog = builder.title(R.string.add_tag)
                .customView(tagLayout, false)
                .autoDismiss(false)
                .negativeText(R.string.dialog_button_cancel)
                .positiveText(R.string.dialog_button_ok)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        Logger.d("You selected" + spinner.getSelectedItemId());
                        Intent intent = new Intent(AnnotationTaskActivity.this, TagEditorActivity.class);
                        intent.putExtra("tag", tag);
                        startActivityForResult(intent, CODE);
                        dialog.dismiss();
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog){
                        super.onNegative(dialog);
                        annotateImageView.undo();
                        dialog.cancel();
                    }
                })
                .build();
    }

    public void showTagDialog(){
        tagDialog.show();
    }

    public void setData(){
        Intent intent = getIntent();
        this.transaction = (Transaction) intent.getSerializableExtra("transaction");
        this.task = (Task) intent.getSerializableExtra("task");
        this.tag = new Gson().fromJson(FileUtils.readAssetFile(this, "annotationTag.json"),
                AnnotationTag.class);
        Logger.d(tag.name);
    }

    public void setView(){
        if (transaction.pictures.size() > 0){
            annotateImage(currentIdx);
        }
        AnnotateImageView.Mode mode =
                annotateImageView.getMode() == AnnotateImageView.Mode.DRAW
                        ? AnnotateImageView.Mode.ZOOM : AnnotateImageView.Mode.DRAW;
        annotateImageView.setMode(mode);

        binding.savePictureCommit.setOnClickListener(view -> showTagDialog());
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
                if (currentIdx < transaction.pictures.size() - 1)
                    annotateImage(++currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_last));
                break;
            case R.id.annotation_prev:
                if (currentIdx > 0)
                    annotateImage(--currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_first));
                break;
            case R.id.annotation_clear:
                annotateImageView.clear();
                break;
            case R.id.annotation_undo:
                annotateImageView.undo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
