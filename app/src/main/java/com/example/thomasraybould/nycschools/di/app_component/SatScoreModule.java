package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.data.SatScoreDataRepoImpl;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoreDataRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.impl.GetSatScoreDataInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SatScoreModule {

    @AppScope
    @Provides
    static GetSatScoreDataInteractor getSatScoreDataInteractor(GetSatScoreDataInteractorImpl getSatScoreDataInteractor){
        return getSatScoreDataInteractor;
    }

    @AppScope
    @Provides
    static SatScoreDataRepo satScoredDataRepo(SatScoreDataRepoImpl satScoredDataRepo){
        return satScoredDataRepo;
    }

}
