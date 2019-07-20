package com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data;

import com.example.thomasraybould.nycschools.entities.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolListResponse {

    private final boolean isSuccessful;
    private final List<School> schools;

    private SchoolListResponse(boolean isSuccessful, List<School> schools) {
        this.isSuccessful = isSuccessful;
        this.schools = schools;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public List<School> getSchools() {
        return schools;
    }

    private static SchoolListResponse createSchoolListResponse(boolean isSuccessful, List<School> schools){
        return new SchoolListResponse(isSuccessful, schools);
    }

    public static SchoolListResponse success(List<School> schools){
        return new SchoolListResponse(true, schools);
    }

    public static SchoolListResponse failure(){
        return new SchoolListResponse(false, new ArrayList<>());
    }


}
