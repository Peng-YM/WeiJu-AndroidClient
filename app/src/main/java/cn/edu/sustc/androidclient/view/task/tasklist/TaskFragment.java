package cn.edu.sustc.androidclient.view.task.tasklist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.TaskFragmentBinding;
import cn.edu.sustc.androidclient.view.base.BaseFragment;

public class TaskFragment extends BaseFragment<TaskFragmentViewModel, TaskFragmentBinding>
        implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    TaskFragmentViewModel fragmentViewModel;

    private TaskAdapter taskAdapter;
    private TaskFragmentBinding fragmentBinding;
    private int transactionStatus = -2;

    public static TaskFragment getInstance() {
        return new TaskFragment();
    }

    public static TaskFragment getInstance(int transactionStatus){
        Bundle args = new Bundle();
        TaskFragment fragment = new TaskFragment();
        fragment.setTransactionStatus(transactionStatus);
        return fragment;
    }

    @Override
    public void onRefresh() {
        taskAdapter.clearItems();
        fetchTasks();
    }

    @Override
    protected TaskFragmentViewModel getViewModel() {
        return fragmentViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.task_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentBinding = getBinding();
        taskAdapter = new TaskAdapter();
        fragmentBinding.recyclerView.
                setLayoutManager(new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL, false));
        fragmentBinding.recyclerView.setAdapter(taskAdapter);
        fragmentBinding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        fragmentBinding.swipeRefreshLayout.setOnRefreshListener(this);
        fragmentBinding.setViewModel(fragmentViewModel);
        if (transactionStatus != -2){
            fragmentViewModel.setStatusFilter(transactionStatus);
        }
        fetchTasks();
    }

    public void fetchTasks() {
        fragmentViewModel.getTasks(taskAdapter).observe(this, resource -> {
            switch (resource.status) {
                case SUCCESS:
                    if (fragmentBinding.swipeRefreshLayout.isRefreshing()) {
                        fragmentBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                    break;
                case ERROR:
                    if (fragmentBinding.swipeRefreshLayout.isRefreshing()) {
                        fragmentBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                    getBaseActivity().showAlertDialog(getString(R.string.error), resource.message);
                    break;
                case LOADING:
                    break;
            }
        });
    }

    public void setTransactionStatus(int transactionStatus){
        this.transactionStatus = transactionStatus;
    }
}
