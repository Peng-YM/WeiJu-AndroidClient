package cn.edu.sustc.androidclient.view.task;

import android.content.SharedPreferences;

import cn.edu.sustc.androidclient.model.MyDataBase;
import cn.edu.sustc.androidclient.model.repository.FileRepository;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.task.annotationtask.AnnotationTaskViewModel;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskViewModel;
import cn.edu.sustc.androidclient.view.task.publishtask.TaskPublishViewModel;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class TaskModule {
    @Provides
    TaskDetailViewModel provideTaskViewModel(MyDataBase dataBase, TaskRepository taskRepository, SharedPreferences sharedPreferences) {
        return new TaskDetailViewModel(dataBase, taskRepository, sharedPreferences);
    }

    @Provides
    AnnotationTaskViewModel provideAnnotationTaskViewModel(FileRepository repository) {
        return new AnnotationTaskViewModel(repository);
    }

    @Provides
    CollectionTaskViewModel provideCollectionTaskViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        return new CollectionTaskViewModel(taskRepository, fileRepository);
    }

    @Provides
    TaskPublishViewModel provideTaskPublishViewModel() {
        return new TaskPublishViewModel();
    }
}
