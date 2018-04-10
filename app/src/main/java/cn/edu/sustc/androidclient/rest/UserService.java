package cn.edu.sustc.androidclient.rest;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.model.Credential;
import cn.edu.sustc.androidclient.model.Session;
import cn.edu.sustc.androidclient.model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface UserService {
    @POST("sessions")
    Observable<MyResponse<Credential>> login(@Body Session session);

    @POST("users")
    Observable<MyResponse<User>> registration(@Body User user);

    // fake apis here
    @GET("credentials/1")
    Observable<MyResponse<Credential>> fakeLogin();
}
