package cn.edu.sustc.androidclient.rest;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginService {
    @POST("/sessions")
    Observable<MyResponse<Credential>> login(@Body Session session);

    @GET("/credentials/1")
    Observable<MyResponse<Credential>> fakeLogin();
}
