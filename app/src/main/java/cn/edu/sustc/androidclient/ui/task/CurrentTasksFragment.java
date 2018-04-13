package cn.edu.sustc.androidclient.ui.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.MyPage;

public class CurrentTasksFragment extends Fragment implements MyPage{
    private String pageTitle;
    private int pageNum;

    public static CurrentTasksFragment newInstance(String pageTitle, int pageNum){
        CurrentTasksFragment fragment = new CurrentTasksFragment();
        Bundle args = new Bundle();
        args.putString("title", pageTitle);
        args.putInt("num", pageNum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = getArguments().getInt("num", 0);
        pageTitle = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_tasks_page, container, false);
        return view;
    }

    @Override
    public String getTitle() {
        return pageTitle;
    }

    @Override
    public int getPageNumber() {
        return pageNum;
    }
}
