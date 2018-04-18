package cn.edu.sustc.androidclient.rest;


import java.util.List;

import cn.edu.sustc.androidclient.common.MyResponse;
import cn.edu.sustc.androidclient.model.Task;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskAPI {
    @GET("tasks")
    Observable<MyResponse<Task>> getTasks(@Query("offset") int offset, @Query("limit") int limit);

    @GET("tasks")
    Observable<MyResponse<List<Task>>> fakeGetTasks();
}
