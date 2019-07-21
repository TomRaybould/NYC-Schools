package com.example.thomasraybould.nycschools.domain.get_school_list_interactor.impl;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.entities.Borough;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSchoolListInteractorImpl implements GetSchoolListInteractor{

    private final SchoolListRepo schoolListRepo;

    @Inject
    GetSchoolListInteractorImpl(SchoolListRepo schoolListRepo) {
        this.schoolListRepo = schoolListRepo;
    }

    @Override
    public Single<SchoolListResponse> getSchoolsByBorough(Borough borough) {
        return schoolListRepo.getSchoolsByBorough(borough);
    }
}
