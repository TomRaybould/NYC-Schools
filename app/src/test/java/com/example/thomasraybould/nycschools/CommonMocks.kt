package com.example.thomasraybould.nycschools

import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider
import io.mockk.every
import io.reactivex.schedulers.Schedulers

class CommonMocks {

    companion object{
        fun setupScheduleProvider(schedulerProvider: SchedulerProvider){
            every { schedulerProvider.io() } returns Schedulers.trampoline()
            every { schedulerProvider.main() } returns Schedulers.trampoline()
            every { schedulerProvider.db() } returns Schedulers.trampoline()
            every { schedulerProvider.computation() } returns Schedulers.trampoline()
        }
    }

}