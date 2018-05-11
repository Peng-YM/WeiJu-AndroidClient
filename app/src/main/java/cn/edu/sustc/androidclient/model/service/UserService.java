package cn.edu.sustc.androidclient.model.service;

import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * REST UserService
 */
public interface UserService {
    /**
     * User Login
     * @param session session
     * @return Credential
     */
    @POST("sessions")
    Observable<MyResponse<Credential>> login(@Body Session session);

    /**
     * User registration
     * @param user new user
     * @return new User
     */
    @POST("users")
    Single<MyResponse<Credential>> registration(@Body User user);

    /**
     * Get user profile
     * @param id user id
     * @return User
     */
    @GET("users/{id}")
    Single<MyResponse<User>> getProfile(@Path("id") String id);

    /**
     * Update User profile
     * @param id user id
     * @return updated user profile
     */
    @PATCH("users/{id}")
    Single<MyResponse<User>> updateProfile(@Path("id") String id, @Body User user);


    // fake apis here
    @GET("credentials/1")
    Single<MyResponse<Credential>> fakeLogin();
}
