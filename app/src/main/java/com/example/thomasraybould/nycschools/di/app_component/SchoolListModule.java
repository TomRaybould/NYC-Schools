package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.data.SchoolListWebRepoImpl;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.impl.GetSchoolListInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module
class SchoolListModule {

    @AppScope
    @Provides
    static SchoolListRepo getSchoolListRepo(SchoolListWebRepoImpl schoolListWebRepo){
        return schoolListWebRepo;
    }

    @AppScope
    @Provides
    static GetSchoolListInteractor getSchoolListInteractor(GetSchoolListInteractorImpl getSchoolListInteractor){
        return getSchoolListInteractor;
    }

}
