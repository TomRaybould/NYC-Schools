package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel;
import com.example.thomasraybould.nycschools.entities.Borough;

import java.util.List;

public interface SchoolListView {

    void setSchoolList(List<SchoolListItemUiModel> schoolListItemUiModels);

    void addItemsForBorough(List<SchoolListItemUiModel> schoolListItemUiModels, Borough borough);

    void removeItemsForBorough(Borough borough);

    void addScoreItem(SchoolListItemUiModel scoreItem);

    void removeScoreItem(String schooldbn);

    void toast(String message);

    void changeBoroughLoadingStatus(Borough borough, boolean isLoading);

    List<SchoolListItemUiModel> getCurrentListItems();
}
