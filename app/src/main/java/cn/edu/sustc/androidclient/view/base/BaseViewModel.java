package cn.edu.sustc.androidclient.view.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import cn.edu.sustc.androidclient.model.Status;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Simple base view model
 */
public abstract class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Status> liveStatus;

    public BaseViewModel(){
        compositeDisposable = new CompositeDisposable();
        liveStatus = new MutableLiveData<>();
        liveStatus.postValue(Status.SUCCESS);
    }

    @Override
    protected void onCleared(){
        compositeDisposable.dispose();
        super.onCleared();
    }

    protected void setStatus(Status status){
        liveStatus.postValue(status);
    }

    public MutableLiveData<Status> getLiveStatus() {
        return liveStatus;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
