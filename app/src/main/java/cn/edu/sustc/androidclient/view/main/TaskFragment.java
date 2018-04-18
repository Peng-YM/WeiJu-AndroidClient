package cn.edu.sustc.androidclient.view.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.CompletedListener;
import cn.edu.sustc.androidclient.databinding.TaskFragmentBinding;
import cn.edu.sustc.androidclient.view.adapter.TaskAdapter;
import cn.edu.sustc.androidclient.viewmodel.TaskFragmentViewModel;

public class TaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CompletedListener {
    private TaskAdapter taskAdapter;
    private TaskFragmentViewModel fragmentViewModel;
    private TaskFragmentBinding fragmentBinding;

    public static TaskFragment getInstance(){
        return new TaskFragment();
    }

    @Override
    public void onRefresh() {
        taskAdapter.clearItems();
        fragmentViewModel.refreshData();
    }

    @Override
    public void onCompleted() {
        if (fragmentBinding.swipeRefreshLayout.isRefreshing()){
            fragmentBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void initData() {
        taskAdapter = new TaskAdapter();
        fragmentBinding.recyclerView.
                setLayoutManager(new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL, false));
        fragmentBinding.recyclerView.setAdapter(taskAdapter);
        fragmentBinding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        fragmentBinding.swipeRefreshLayout.setOnRefreshListener(this);

        fragmentViewModel = new TaskFragmentViewModel(taskAdapter, this);
        fragmentBinding.setViewModel(fragmentViewModel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.task_fragment, container, false);
        fragmentBinding = TaskFragmentBinding.bind(contentView);
        initData();
        return contentView;
    }
}
