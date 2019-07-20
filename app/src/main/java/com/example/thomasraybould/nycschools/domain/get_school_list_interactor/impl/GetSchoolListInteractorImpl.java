package com.example.thomasraybould.nycschools.domain.get_school_list_interactor.impl;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;

import io.reactivex.Single;

public class GetSchoolListInteractorImpl implements GetSchoolListInteractor{

    private final SchoolListRepo schoolListRepo;

    public GetSchoolListInteractorImpl(SchoolListRepo schoolListRepo) {
        this.schoolListRepo = schoolListRepo;
    }

    @Override
    public Single<SchoolListResponse> getSchools() {
        return schoolListRepo.getSchools();
    }
}
