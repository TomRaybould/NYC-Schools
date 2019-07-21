package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItem;

import java.util.List;

public interface SchoolListView {

    void setSchoolList(List<SchoolListItem> schoolListItems);

}
