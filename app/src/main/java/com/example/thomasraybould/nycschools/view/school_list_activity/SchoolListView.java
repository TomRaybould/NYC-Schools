package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItem;
import com.example.thomasraybould.nycschools.entities.Borough;

import java.util.List;

public interface SchoolListView {

    void setSchoolList(List<SchoolListItem> schoolListItems);

    void addItemsForBorough(List<SchoolListItem> schoolListItems, Borough borough);

    void removeItemsForBorough(Borough borough);

    void addScoreItem(SchoolListItem scoreItem);

    void removeScoreItem(String schooldbn);

}
