package com.example.thomasraybould.nycschools.domain.get_school_list_interactor;

import com.example.thomasraybould.nycschools.entities.School;

import java.util.List;

import io.reactivex.Completable;

public interface SchoolListDbRepo extends SchoolListRepo{

    Completable storeSchools(List<School> schools);

}
