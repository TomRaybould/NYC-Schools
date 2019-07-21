package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.view.base.Presenter;

public interface SchoolListPresenter extends Presenter<SchoolListView>{

    void onBoroughSelected(Borough borough);

}
