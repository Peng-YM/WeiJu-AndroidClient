package cn.edu.sustc.androidclient.view.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.BaseActivity;
import cn.edu.sustc.androidclient.view.adapter.CustomPagerAdapter;

public class TaskManagerActivity extends BaseActivity {
    public static void start(Context context){
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
