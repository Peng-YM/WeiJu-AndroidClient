package cn.edu.sustc.androidclient.view.task.publishtask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.DatePicker;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskPublishBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class TaskPublishActivity extends BaseActivity<TaskPublishViewModel, ActivityTaskPublishBinding> {
    @Inject
    TaskPublishViewModel viewModel;
    @Inject
    AwesomeValidation validation;
    @Inject
    SharedPreferences preferences;

    private ActivityTaskPublishBinding binding;
    private Task task;
    private static final int CODE = 2;
    private Calendar myCalendar;

    public static void start(Context context) {
        Intent intent = new Intent(context, TaskPublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        this.myCalendar = Calendar.getInstance();
        this.task = new Task();
        // default description is empty
        task.description = "";

        setValidation();
        setWidget();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_publish;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE){
            task.description = data.getStringExtra("HTML");
        }
    }

    private void setValidation() {
        validation.addValidation(binding.taskName, s -> s.trim().length() != 0,
                getString(R.string.alert_field_empty));
        validation.addValidation(binding.taskSize, s -> s.trim().length() != 0,
                getString(R.string.alert_field_empty));
        validation.addValidation(binding.taskSize, Pattern.compile("^[0-9]*$"),
                getString(R.string.alert_field_number));
    }

    private void setWidget() {
        DatePickerDialog.OnDateSetListener startDateListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yy/MM/dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
            String time = simpleDateFormat.format(myCalendar.getTime());
            binding.taskStart.setText(time);
            task.start = String.valueOf(myCalendar.getTime());
        };

        DatePickerDialog.OnDateSetListener endDateListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yy/MM/dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
            String time = simpleDateFormat.format(myCalendar.getTime());
            binding.taskEnd.setText(time);
            task.end = String.valueOf(myCalendar.getTime());
        };
        // setup go to rich editor
        binding.editorButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RichEditorActivity.class);
            startActivityForResult(intent, CODE);
        });
        // setup publish button
        binding.publishButton.setOnClickListener(view -> publishTask());
        // setup task cover
        binding.taskCover.setOnClickListener(view -> Album.image(this)
                .singleChoice()
                .columnCount(3)
                .camera(true)
                .onResult((requestCode, result) -> {
                    // show selected image
                    AlbumFile selected = result.get(0);
                    RequestOptions options = new RequestOptions()
                            .centerCrop();
                    Glide.with(this)
                            .load(new File(selected.getPath()))
                            .apply(options)
                            .into(binding.taskCover);
                    // upload picture to server
                    viewModel.uploadCover(selected.getPath()).observe(this, resource -> {
                        switch (resource.status) {
                            case SUCCESS:
                                task.cover = resource.data;
                                break;
                            case LOADING:
                                break;
                            case ERROR:
                                showAlertDialog(getString(R.string.error), getString(R.string.upload_failed));
                        }
                    });
                }).start());
        // setup date picker
        binding.taskStart.setOnClickListener(view -> {
            new DatePickerDialog(TaskPublishActivity.this, startDateListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        binding.taskEnd.setOnClickListener(view -> {
            new DatePickerDialog(TaskPublishActivity.this, endDateListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void publishTask(){
        if (validation.validate()) {
            task.name = binding.taskName.getText().toString();
            task.type = 0;
            task.start = String.valueOf(new Date().getTime());
            task.author = preferences.getInt("id", 0);
            // TODO: task end
            task.end = String.valueOf(new Date().getTime());
            viewModel.publishTask(task).observe(this, resource -> {
                showLoading();
                switch (resource.status) {
                    case SUCCESS:
                        hideLoading();
                        showAlertDialog(getString(R.string.info), getString(R.string.publish_success));
                        finish();
                        break;
                    case ERROR:
                        hideLoading();
                        showAlertDialog(getString(R.string.error), getString(R.string.publish_error));
                        break;
                    case LOADING:
                        break;
                }
            });
        }
    }
}
