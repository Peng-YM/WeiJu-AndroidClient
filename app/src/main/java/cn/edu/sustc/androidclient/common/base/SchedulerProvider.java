package cn.edu.sustc.androidclient.common.base;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();
    Scheduler io();
}
