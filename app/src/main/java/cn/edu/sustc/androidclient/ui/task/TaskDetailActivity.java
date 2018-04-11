package cn.edu.sustc.androidclient.ui.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.edu.sustc.androidclient.R;

public class TaskDetailActivity extends AppCompatActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, TaskDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }
}
