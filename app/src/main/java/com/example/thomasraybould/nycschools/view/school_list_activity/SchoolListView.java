package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel;
import com.example.thomasraybould.nycschools.entities.Borough;

import java.util.List;

public interface SchoolListView {

    void toast(String message);

    void changeBoroughLoadingStatus(Borough borough, boolean isLoading);

    List<SchoolListItemUiModel> getCurrentListItems();
}
