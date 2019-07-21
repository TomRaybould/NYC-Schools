package com.example.thomasraybould.nycschools.domain.get_sat_score_interactor;

import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse;

import io.reactivex.Single;

public interface SatScoredDataRepo {

    Single<SatDataResponse> getSatScoreDataByDbn(String dbn);

}
