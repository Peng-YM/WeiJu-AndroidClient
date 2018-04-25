package cn.edu.sustc.androidclient.model.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.common.AppSchedulerProvider;
import cn.edu.sustc.androidclient.common.RetrofitFactory;
import cn.edu.sustc.androidclient.common.base.BaseViewModel;
import cn.edu.sustc.androidclient.common.base.SchedulerProvider;
import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.service.FileService;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

public class FileRepository implements BaseViewModel {
    @Deprecated
    private static FileRepository instance;

    private FileService fileService;
    private SchedulerProvider schedulerProvider;

    // data
    private CompositeDisposable disposables = new CompositeDisposable();

    @Deprecated
    private FileRepository(FileService fileService) {
        this.fileService = fileService;
    }

    @Inject
    public FileRepository(FileService service, AppSchedulerProvider schedulerProvider) {
        this.fileService = service;
        this.schedulerProvider = schedulerProvider;
    }

    @Deprecated
    public static FileRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new FileRepository(
                            RetrofitFactory.getInstance().create(FileService.class)
                    );
                }
            }
        }
        return instance;
    }

    public LiveData<MyResource<File>> download(String url, String pathToSave) {
        MutableLiveData<MyResource<File>> target = new MutableLiveData<>();
        target.postValue(MyResource.loading(null));

        this.fileService
                .downloadFile(url)
                .flatMap(responseBodyResponse -> saveToDisk(responseBodyResponse, pathToSave))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(File file) {
                        target.postValue(MyResource.success(file));
                    }

                    @Override
                    public void onError(Throwable e) {
                        target.postValue(MyResource.error(e.getMessage(), null));
                    }
                });
        return target;
    }

    public void upload(String url, File file, SingleObserver observer) {
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(file.getAbsolutePath()),
                file
        );
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
        this.fileService
                .upload(url, body)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(observer);
    }


    private Single<File> saveToDisk(final Response<ResponseBody> response, final String pathToSave) {
        return Single.create(emitter -> {
            try {
                File destination = new File(pathToSave);
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(destination));
                bufferedSink.writeAll(response.body().source());
                bufferedSink.close();
                emitter.onSuccess(destination);
            } catch (IOException | NullPointerException e) {
                Logger.e(e.getMessage());
                emitter.onError(e);
            }
        });
    }

    @Override
    public void onClear() {
        disposables.dispose();
    }

}
