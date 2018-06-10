package cn.edu.sustc.androidclient.view.task.tasklist;

import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class TaskFragmentModule {
    @Provides
    TaskFragmentViewModel taskFragmentViewModel(TaskRepository taskRepository) {
        return new TaskFragmentViewModel(taskRepository);
    }
}
