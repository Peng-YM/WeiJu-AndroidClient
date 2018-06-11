package cn.edu.sustc.androidclient.model.service;


import java.util.List;

import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskService {
    @GET("api/tasks/")
    Observable<MyResponse<List<Task>>> getTasks(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @GET("api/tasks/apply/")
    Single<MyResponse<Transaction>> applyTask(@Body TransactionInfo info);

}
