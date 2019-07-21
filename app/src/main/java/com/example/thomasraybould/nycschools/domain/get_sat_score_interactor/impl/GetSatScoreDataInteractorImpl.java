package com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.impl;

import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoreDataRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSatScoreDataInteractorImpl implements GetSatScoreDataInteractor{

    private final SatScoreDataRepo satScoreDataRepo;

    @Inject
    public GetSatScoreDataInteractorImpl(SatScoreDataRepo satScoreDataRepo) {
        this.satScoreDataRepo = satScoreDataRepo;
    }

    @Override
    public Single<SatDataResponse> getSatScoreDataByDbn(String dbn) {
        return satScoreDataRepo.getSatScoreDataByDbn(dbn);
    }

}
