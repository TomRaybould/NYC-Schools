package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.data.SatScoreDataDbRepoImpl;
import com.example.thomasraybould.nycschools.data.SatScoreDataRepoImpl;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoreDataDbRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoreDataRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.impl.GetSatScoreDataInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class SatScoreModule {

    @Singleton
    @Provides
    static GetSatScoreDataInteractor getSatScoreDataInteractor(GetSatScoreDataInteractorImpl getSatScoreDataInteractor){
        return getSatScoreDataInteractor;
    }

    @Singleton
    @Provides
    static SatScoreDataRepo satScoredDataRepo(SatScoreDataRepoImpl satScoredDataRepo){
        return satScoredDataRepo;
    }

    @Singleton
    @Provides
    static SatScoreDataDbRepo satScoredDataDbRepo(SatScoreDataDbRepoImpl satScoredDataRepo){
        return satScoredDataRepo;
    }

}
