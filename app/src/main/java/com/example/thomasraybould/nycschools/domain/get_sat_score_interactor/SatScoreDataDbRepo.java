package com.example.thomasraybould.nycschools.domain.get_sat_score_interactor;

import com.example.thomasraybould.nycschools.entities.SatScoreData;

import io.reactivex.Completable;

public interface SatScoreDataDbRepo extends SatScoreDataRepo{

    Completable storeSatData(SatScoreData satScoreData);

}
