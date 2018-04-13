package cn.edu.sustc.androidclient.ui.task;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import cn.edu.sustc.androidclient.model.Task;

public class TaskDetailViewModel extends BaseObservable {
    private ObservableField<Task> task;
    private TextView taskDescriptionTv;

    public TaskDetailViewModel(Task task, TextView textView){
        initData();
        this.taskDescriptionTv = textView;
        setTask(task);
        displayTaskDescription();
    }

    public void setTask(Task task) {
        this.task.set(task);
    }

    public ObservableField<Task> getTask() {
        return task;
    }

    public void displayTaskDescription(){
        RichText.fromMarkdown(task.get().descriptions)
                .into(this.taskDescriptionTv);
    }

    private void initData(){
        this.task = new ObservableField<>();
    }


}
