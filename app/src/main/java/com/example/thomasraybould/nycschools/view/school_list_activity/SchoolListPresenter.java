package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel;
import com.example.thomasraybould.nycschools.view.base.Presenter;

public interface SchoolListPresenter extends Presenter<SchoolListView>{

    void onSchoolListItemSelected(SchoolListItemUiModel schoolListItemUiModel);
}
