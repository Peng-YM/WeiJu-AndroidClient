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
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;
import cn.edu.sustc.androidclient.model.data.AnnotationCommits;
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
    private MaterialDialog tagDialog;
    private Task.AnnotationTaskFormatter formatter;
    private AnnotationCommits commits;


    private int tagCounter = 0;
    private int currentIdx = 0;
    private Shape currentShape;
    private Bitmap currentBitmap;

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
        initData();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CODE){
            AnnotationTag resultTag = (AnnotationTag) data.getSerializableExtra("tag");
            resultTag.positions = currentShape.getCriticalPoints();
            this.commits.tags.add(new Gson().toJson(resultTag));
            this.commits.pictures.add(transaction.pictures.get(currentIdx));
        }else{
            annotateImageView.undo();
            tagCounter--;
        }
    }

    private void initData() {
        Intent intent = getIntent();
        this.transaction = (Transaction) intent.getSerializableExtra("transaction");
        this.commits = new AnnotationCommits();
        this.commits.transactionId = transaction.transactionId;
        this.commits.tags = new ArrayList<>();
        this.commits.pictures = new ArrayList<>();
        // get formatter
        viewModel.getFormatter(transaction.taskId).observe(this, resource -> {
            showLoading();
            switch (resource.status){
                case SUCCESS:
                    hideLoading();
                    this.formatter = resource.data;
                    initTagSelectionDialog();
                    break;
                case ERROR:
                    hideLoading();
                    showAlertDialog(getString(R.string.error), resource.message);
                    break;
                default:
                case LOADING:
                    break;
            }
        });
    }

    private void initView() {
        if (transaction.pictures.size() > 0) {
            onImageChanged(currentIdx);
        }
        annotateImageView = binding.annotateImageView;
        annotateImageView.setShapeListener((shape)->{
            currentShape = shape;
            showTagDialog();
            tagCounter++;
        });
        binding.savePictureCommit.setOnClickListener(view -> {
            viewModel.uploadCommits(commits).observe(this, resource -> {
                showLoading();
                switch (resource.status){
                    case SUCCESS:
                        hideLoading();
                        showAlertDialog(getString(R.string.info), getString(R.string.commit_success));
                        finish();
                        break;
                    case ERROR:
                        hideLoading();
                        showAlertDialog(getString(R.string.error), getString(R.string.commit_error));
                        break;
                    case LOADING:
                        break;
                }
            });
        });
    }

    private void initTagSelectionDialog(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View stdView = factory.inflate(R.layout.dialog_custom, null);
        LinearLayout tagLayout = stdView.findViewById(R.id.tag_dialog_layout);
        TextView tagDescription = tagLayout.findViewById(R.id.tag_dialog_guide);
        tagDescription.setText(getString(R.string.tag_prompt));
        Spinner spinner = tagLayout.findViewById(R.id.tag_dialog_spinner);
        // fill spinner with tag list
        ArrayList<String> tagNameList = new ArrayList<>();
        for (AnnotationTag tag: formatter.tags){
            tagNameList.add(tag.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tagNameList);
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
                        int selectedIndex = spinner.getSelectedItemPosition();
                        Intent intent = new Intent(AnnotationTaskActivity.this, TagEditorActivity.class);
                        intent.putExtra("tag", formatter.tags.get(selectedIndex));
                        intent.putExtra("url", transaction.pictures.get(currentIdx));
                        startActivityForResult(intent, CODE);
                        // set tag image
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
                        currentBitmap = resource;
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
                    onImageChanged(++currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_last));
                break;
            case R.id.annotation_prev:
                if (currentIdx > 0)
                    onImageChanged(--currentIdx);
                else
                    showAlertDialog(getString(R.string.alert), getString(R.string.alert_first));
                break;
            case R.id.annotation_clear:
                annotateImageView.clear();
                tagCounter = 0;
                break;
            case R.id.annotation_undo:
                if (tagCounter > 1) {
                    tagCounter -= 1;
                    annotateImageView.undo();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onImageChanged(int index){
        String title = String.format("%s(%d/%d)", getString(R.string.annotation_task), currentIdx + 1, transaction.pictures.size());
        setTitle(title);
        if (index == transaction.pictures.size() - 1)
            binding.savePictureCommit.setVisibility(View.VISIBLE);
        else
            binding.savePictureCommit.setVisibility(View.GONE);
        tagCounter = 0;
        currentShape = null;
        currentBitmap =  null;
        currentIdx = index;
        annotateImage(index);
    }
}
