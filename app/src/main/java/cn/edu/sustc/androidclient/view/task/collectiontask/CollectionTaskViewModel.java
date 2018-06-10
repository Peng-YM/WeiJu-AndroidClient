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

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.repository.FileRepository;
import cn.edu.sustc.androidclient.model.repository.TaskRepository;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CollectionTaskViewModel extends ViewModel {
    private final static String FILE_URL = "http://206.189.35.98:12000/api/file/";
    private TaskRepository taskRepository;
    private FileRepository fileRepository;
    private CompositeDisposable disposables;

    public CollectionTaskViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
        disposables = new CompositeDisposable();
    }

    public MutableLiveData<MyResource<Float>> uploadImages(ArrayList<AlbumFile> albumFiles) {
        MutableLiveData<MyResource<Float>> progress = new MutableLiveData<>();
        MyResource<Float> resource = MyResource.loading(0f);
        progress.postValue(resource);

        int total = albumFiles.size();
        // thread safe counter
        AtomicInteger counter = new AtomicInteger(0);
        ArrayList<String> urls = new ArrayList<>();

        for (AlbumFile file : albumFiles) {
            SingleObserver<MyResponse<List<String>>> observer = new SingleObserver<MyResponse<List<String>>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disposables.add(d);
                }

                @Override
                public void onSuccess(MyResponse<List<String>> urlResponse) {
                    counter.incrementAndGet();
                    urls.add(urlResponse.data.get(0));

                    if (counter.get() < total) {
                        MyResource<Float> resource = MyResource.loading(((float) counter.get() / total) * 100);
                        progress.postValue(resource);
                    } else {
                        MyResource<Float> resource = MyResource.success(100f);
                        progress.postValue(resource);
                        Logger.d("Uploaded %d file: %s\nURL:%s", total, file.getPath(), TextUtils.join("\n", urls));
                    }
                }

                @Override
                public void onError(Throwable e) {
                    progress.postValue(MyResource.error("上传失败！", ((float) counter.get() / total) * 100));
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
