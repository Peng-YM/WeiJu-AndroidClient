package cn.edu.sustc.androidclient.ui.task;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.common.MyPage;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_PAGES = 2;
    private List<MyPage> pages;
    public CustomPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        pages = new ArrayList<>();
        pages.add(CurrentTasksFragment.newInstance("进行中", 0));
        pages.add(FinishedTasksFragment.newInstance("已完成", 1));
        Logger.d(1);
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) pages.get(position);
    }

    @Override
    public int getCount() {
        Logger.d(3);
        return NUM_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Logger.d(4);
        MyPage page = pages.get(position);
        return page.getTitle();
    }
}
