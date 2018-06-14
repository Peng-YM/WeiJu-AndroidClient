package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.arch.lifecycle.MutableLiveData;
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
import cn.edu.sustc.androidclient.view.base.BaseViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class CollectionTaskViewModel extends BaseViewModel {
    private TaskRepository taskRepository;
    private FileRepository fileRepository;

    public CollectionTaskViewModel(TaskRepository taskRepository, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
    }

    public MutableLiveData<MyResource<Float>> uploadImages(ArrayList<AlbumFile> albumFiles, String URL) {
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
                    getCompositeDisposable().add(d);
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
            fileRepository.upload(URL, new File(file.getPath()), observer);
        }
        return progress;
    }

    public void commit(int transactionId){

        taskRepository.finishTask(transactionId);
    }
}
