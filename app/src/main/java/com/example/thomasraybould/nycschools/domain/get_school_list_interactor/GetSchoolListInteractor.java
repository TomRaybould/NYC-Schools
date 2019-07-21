package com.example.thomasraybould.nycschools.domain.get_school_list_interactor;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.entities.Borough;

import io.reactivex.Single;

public interface GetSchoolListInteractor {

    Single<SchoolListResponse> getSchoolsByBorough(Borough borough);

}
