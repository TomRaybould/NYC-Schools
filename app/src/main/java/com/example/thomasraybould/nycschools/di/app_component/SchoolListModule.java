package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.data.SchoolListDbRepoImpl;
import com.example.thomasraybould.nycschools.data.SchoolListWebRepoImpl;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.SchoolListDbRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.impl.GetSchoolListInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class SchoolListModule {

    @Singleton
    @Provides
    static SchoolListRepo getSchoolListRepo(SchoolListWebRepoImpl schoolListWebRepo){
        return schoolListWebRepo;
    }

    @Singleton
    @Provides
    static SchoolListDbRepo getSchoolListDbRepo(SchoolListDbRepoImpl schoolListDbRepo){
        return schoolListDbRepo;
    }

    @Singleton
    @Provides
    static GetSchoolListInteractor getSchoolListInteractor(GetSchoolListInteractorImpl getSchoolListInteractor){
        return getSchoolListInteractor;
    }

}
