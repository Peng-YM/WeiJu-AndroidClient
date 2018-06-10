package cn.edu.sustc.androidclient.view.task.taskmanager;

import cn.edu.sustc.androidclient.R;

public enum Pages {
    PROCESSING(R.string.processing_task, R.layout.current_tasks_page),
    FINISHED(R.string.finished_task, R.layout.finished_tasks_page);
    private int titleId;
    private int layoutId;

    Pages(int titleId, int layoutId) {
        this.titleId = titleId;
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getTitleId() {
        return titleId;
    }
}
