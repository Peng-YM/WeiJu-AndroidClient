package cn.edu.sustc.androidclient.view.task;

import android.app.Application;
import android.content.SharedPreferences;

import cn.edu.sustc.androidclient.model.repository.FileRepository;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import cn.edu.sustc.androidclient.view.task.annotationtask.AnnotationTaskViewModel;
import cn.edu.sustc.androidclient.view.task.collectiontask.CollectionTaskViewModel;
import cn.edu.sustc.androidclient.view.task.publishtask.TaskPublishViewModel;
import cn.edu.sustc.androidclient.view.task.taskdetail.TaskDetailViewModel;
import cn.edu.sustc.androidclient.view.task.taskmanager.CustomPagerAdapter;
import cn.edu.sustc.androidclient.view.task.taskmanager.TaskManagerActivity;
import cn.edu.sustc.androidclient.view.task.taskmanager.TaskManagerViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class TaskModule {
    @Provides
    TaskDetailViewModel provideTaskViewModel(TaskRepository taskRepository, SharedPreferences sharedPreferences) {
        return new TaskDetailViewModel(taskRepository, sharedPreferences);
    }

    @Provides
    AnnotationTaskViewModel provideAnnotationTaskViewModel(TaskRepository taskRepository) {
        return new AnnotationTaskViewModel(taskRepository);
    }

    @Provides
    CollectionTaskViewModel provideCollectionTaskViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        return new CollectionTaskViewModel(taskRepository, fileRepository);
    }

    @Provides
    TaskPublishViewModel provideTaskPublishViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        return new TaskPublishViewModel(taskRepository, fileRepository);
    }

    @Provides
    TaskManagerViewModel provideTaskManagerViewModel(TaskRepository taskRepository){
        return new TaskManagerViewModel(taskRepository);
    }

    @Provides
    CustomPagerAdapter provideCustomPagerAdapter(Application application, TaskManagerActivity taskManagerActivity){
        return new CustomPagerAdapter(application, taskManagerActivity.getSupportFragmentManager());
    }
}
