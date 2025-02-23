package com.example.thomasraybould.nycschools.rx_util

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler

    fun computation(): Scheduler

    fun main(): Scheduler

    fun db(): Scheduler
}
