package com.example.thomasraybould.nycschools.rx_util;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler main();

}
