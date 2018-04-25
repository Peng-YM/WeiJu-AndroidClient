package cn.edu.sustc.androidclient.model.service;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface FileService {
    @Streaming
    @GET
    Single<Response<ResponseBody>> downloadFile(@Url String fileUrl);

    @Multipart
    @POST
    Single<Response<ResponseBody>> upload(
            @Url String url,
            @Part MultipartBody.Part file
    );
}
