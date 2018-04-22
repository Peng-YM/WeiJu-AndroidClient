package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.io.File;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.repository.FileRepository;

public class AnnotationTaskViewModel extends ViewModel {
    private FileRepository fileRepository;

    @Inject
    public AnnotationTaskViewModel(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public LiveData<MyResource<File>> downloadFile(String url, String path){
        return fileRepository.download(url, path);
    }
}
