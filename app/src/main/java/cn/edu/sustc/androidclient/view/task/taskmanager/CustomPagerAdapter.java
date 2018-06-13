package cn.edu.sustc.androidclient.view.task.taskmanager;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.orhanobut.logger.Logger;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.view.task.tasklist.TaskFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    private Application context;

    public CustomPagerAdapter(Application context, FragmentManager fragmentManager){
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Logger.d("所有任务");
                return TaskFragment.getInstance(-1);
            case 1:
                Logger.d("进行中");
                return TaskFragment.getInstance(Transaction.TransactionStatus.PROGRESSING);
            case 2:
            default:
                Logger.d("已完成");
                return TaskFragment.getInstance(Transaction.TransactionStatus.FINISHED);
        }
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
                return context.getString(R.string.my_all_tasks);
            case 1:
                return context.getString(R.string.processing_task);
            case 2:
                return context.getString(R.string.finished_task);
        }
    }
}
