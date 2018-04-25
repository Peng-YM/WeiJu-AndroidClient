package cn.edu.sustc.androidclient.view.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cn.edu.sustc.androidclient.R;

public class TaskManagerActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, TaskManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        ViewPager viewPager = findViewById(R.id.task_manager_pager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
    }
}
