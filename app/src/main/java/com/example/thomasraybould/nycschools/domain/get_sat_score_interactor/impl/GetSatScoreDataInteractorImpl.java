package com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.impl;

import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoreDataDbRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoreDataRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse;
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;

public class GetSatScoreDataInteractorImpl implements GetSatScoreDataInteractor{

    private final SatScoreDataRepo satScoreDataWebRepo;
    private final SatScoreDataDbRepo satScoreDataDbRepo;
    private final SchedulerProvider schedulerProvider;

    @Inject
    GetSatScoreDataInteractorImpl(SatScoreDataRepo satScoreDataWebRepo, SatScoreDataDbRepo satScoreDataDbRepo, SchedulerProvider schedulerProvider) {
        this.satScoreDataWebRepo = satScoreDataWebRepo;
        this.satScoreDataDbRepo = satScoreDataDbRepo;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<SatDataResponse> getSatScoreDataByDbn(String dbn) {
        return satScoreDataDbRepo.getSatScoreDataByDbn(dbn)
                .flatMap(satDataResponse -> {
                    if(satDataResponse.isSuccessful()){
                        return Single.just(satDataResponse);
                    }
                    return getSatScoreFromWeb(dbn);
                });
    }

    private SingleSource<SatDataResponse> getSatScoreFromWeb(String dbn) {
        return satScoreDataWebRepo.getSatScoreDataByDbn(dbn)
                .subscribeOn(schedulerProvider.io())
                .flatMap(this::cacheSatResult);
    }

    private SingleSource<SatDataResponse> cacheSatResult(SatDataResponse satDataResponse) {
        if(satDataResponse.isSuccessful()){
            return satScoreDataDbRepo.storeSatData(satDataResponse.getSatScoreData())
                    .andThen(Single.just(satDataResponse));
        }
        return Single.just(satDataResponse);
    }

}
