package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.repository.FileRepository;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CollectionTaskViewModel extends ViewModel {
    private TaskRepository taskRepository;
    private FileRepository fileRepository;
    private final static String FILE_URL = "http://206.189.35.98:12000/api/file/";
    private CompositeDisposable disposables;

    @Inject
    public CollectionTaskViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
        disposables = new CompositeDisposable();
    }

    public MutableLiveData<Float> uploadImages(ArrayList<AlbumFile> albumFiles){
        MutableLiveData<Float> progress = new MutableLiveData<>();
        progress.postValue(0f);
        int total = albumFiles.size();
        // thread safe counter
        AtomicInteger counter = new AtomicInteger(0);
        for (AlbumFile file: albumFiles){
            SingleObserver<MyResponse<List<String>>> observer = new SingleObserver<MyResponse<List<String>>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disposables.add(d);
                }

                @Override
                public void onSuccess(MyResponse<List<String>> urlResponse) {
                    counter.incrementAndGet();
                    progress.postValue(((float)counter.get() / total) * 100);
                    Logger.d("Uploaded file: %s\nURL:%s", file.getPath(), urlResponse.data);
                }

                @Override
                public void onError(Throwable e) {
                    Logger.e("Unable to upload file: \n");
                    e.printStackTrace();
                }
            };
            fileRepository.upload(FILE_URL, new File(file.getPath()), observer);
        }
        return progress;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }
}
