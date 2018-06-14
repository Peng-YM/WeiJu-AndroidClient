package cn.edu.sustc.androidclient.view.task.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.ActivityTaskManagerBinding;
import cn.edu.sustc.androidclient.view.base.BaseActivity;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class TaskManagerActivity extends BaseActivity<TaskManagerViewModel, ActivityTaskManagerBinding> implements HasSupportFragmentInjector{
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    CustomPagerAdapter pagerAdapter;

    private ActivityTaskManagerBinding binding;
    private PagerTabStrip PagerTab;

    public static void start(Context context) {
        Intent intent = new Intent(context, TaskManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();

        ViewPager viewPager = binding.taskManagerPager;
        viewPager.setAdapter(pagerAdapter);

        PagerTab = findViewById(R.id.pager_header);
        //设置Tab选中时的颜色
        PagerTab.setTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        PagerTab.setDrawFullUnderline(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_manager;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
