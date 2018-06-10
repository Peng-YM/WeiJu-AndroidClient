package cn.edu.sustc.androidclient.common;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler io();
}
