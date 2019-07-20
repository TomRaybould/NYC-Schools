package com.example.thomasraybould.nycschools.RxUtil;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler main();

}
