package cn.edu.sustc.androidclient.model.service;

import cn.edu.sustc.androidclient.model.MyRequest;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
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
     *
     * @param session session
     * @return Credential
     */
    @POST("auth/api/login")
    Single<MyResponse<Credential>> login(@Body MyRequest<Session> session);

    /**
     * User registration
     *
     * @param session new user
     * @return new User
     */
    @POST("auth/api/register")
    Single<MyResponse<User>> registration(@Body MyRequest<Session> session);

    /**
     * Get user profile
     *
     * @param id user id
     * @return User
     */
    @GET("api/users/{id}/")
    Single<MyResponse<User>> getProfile(@Path("id") int id);

    /**
     * Update User profile
     *
     * @param id user id
     * @return updated user profile
     */
    @PATCH("api/users/{id}/")
    Single<MyResponse<User>> updateProfile(@Path("id") int id, @Body MyRequest<User> user);
}
