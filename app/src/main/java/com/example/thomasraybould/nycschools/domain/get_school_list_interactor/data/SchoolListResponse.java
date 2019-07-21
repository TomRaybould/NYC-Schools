package com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data;

import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.School;

import java.util.ArrayList;
import java.util.List;

public class SchoolListResponse {

    private final boolean isSuccessful;
    private final Borough borough;
    private final List<School> schools;

    private SchoolListResponse(boolean isSuccessful, Borough borough, List<School> schools) {
        this.isSuccessful = isSuccessful;
        this.borough = borough;
        this.schools = schools;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public List<School> getSchools() {
        return schools;
    }

    public Borough getBorough() {
        return borough;
    }

    public static SchoolListResponse success(List<School> schools, Borough borough){
        return new SchoolListResponse(true, borough, schools);
    }

    public static SchoolListResponse failure(){
        return new SchoolListResponse(false, null, new ArrayList<>());
    }


}
