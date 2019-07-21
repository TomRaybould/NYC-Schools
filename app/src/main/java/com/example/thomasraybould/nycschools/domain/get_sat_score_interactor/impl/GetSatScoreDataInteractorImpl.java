package com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.impl;

import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoredDataRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSatScoreDataInteractorImpl implements GetSatScoreDataInteractor{

    private final SatScoredDataRepo satScoredDataRepo;

    @Inject
    public GetSatScoreDataInteractorImpl(SatScoredDataRepo satScoredDataRepo) {
        this.satScoredDataRepo = satScoredDataRepo;
    }

    @Override
    public Single<SatDataResponse> getSatScoreDataByDbn(String dbn) {
        return satScoredDataRepo.getSatScoreDataByDbn(dbn);
    }

}
