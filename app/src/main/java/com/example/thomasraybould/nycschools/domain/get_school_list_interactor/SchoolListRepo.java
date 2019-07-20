package com.example.thomasraybould.nycschools.domain.get_school_list_interactor;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;

import io.reactivex.Single;


public interface SchoolListRepo {

    Single<SchoolListResponse> getSchools();

}
