package cn.edu.sustc.androidclient.view.task.tasklist;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.databinding.TaskItemBinding;
import cn.edu.sustc.androidclient.model.data.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.BindingHolder> {
    private List<Task> tasks;

    public TaskAdapter() {
        tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public TaskAdapter.BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding itemBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.task_item,
                        parent,
                        false);
        return new BindingHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.BindingHolder holder, int position) {
        TaskItemViewModel itemViewModel = new TaskItemViewModel(tasks.get(position));
        holder.itemBinding.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addItem(Task task) {
        tasks.add(task);
        notifyItemInserted(getItemCount() - 1);
    }

    public void clearItems() {
        tasks.clear();
        notifyDataSetChanged();
    }

    static class BindingHolder extends RecyclerView.ViewHolder {
        private TaskItemBinding itemBinding;

        BindingHolder(TaskItemBinding itemBinding) {
            super(itemBinding.cardView);
            this.itemBinding = itemBinding;
        }
    }
}
