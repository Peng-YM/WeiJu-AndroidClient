package cn.edu.sustc.androidclient.view.task.taskmanager;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.view.task.tasklist.TaskFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private Application context;

    public CustomPagerAdapter(Application context, FragmentManager fragmentManager){
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return TaskFragment.getInstance();
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            default:
            case 0:
                return context.getString(R.string.processing_task);
            case 1:
                return context.getString(R.string.finished_task);
        }
    }
}
