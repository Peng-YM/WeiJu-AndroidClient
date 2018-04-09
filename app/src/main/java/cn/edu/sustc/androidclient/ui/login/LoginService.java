package cn.edu.sustc.androidclient.ui.login;

import android.databinding.ObservableField;

import cn.edu.sustc.androidclient.model.Session;
import retrofit2.http.Body;
import retrofit2.http.GET;
import rx.Observable;

import cn.edu.sustc.androidclient.data.Credential;
import cn.edu.sustc.androidclient.data.MyResponse;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/sessions")
    Observable<MyResponse<Credential>> login(@Body Session session);

    @GET("/credentials/1")
    Observable<MyResponse<Credential>> fakeLogin();
}
