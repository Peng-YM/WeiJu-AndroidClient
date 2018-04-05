package cn.edu.sustc.androidclient.api;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIInterface {
    @GET
    Call<JsonObject> doGet(@Url String url);

    @Headers("Content-Type: application/json")
    @POST
    Call<JsonObject> doPost(@Url String url, @Body JsonObject body);

    @PATCH
    Call<JsonObject> doPatch(@Url String url);

    @DELETE
    Call<JsonObject> doDelete(@Url String url);
}
