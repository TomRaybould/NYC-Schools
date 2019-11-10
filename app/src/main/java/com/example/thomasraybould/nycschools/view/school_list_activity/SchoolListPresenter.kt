package com.example.thomasraybould.nycschools.view.school_list_activity

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import com.example.thomasraybould.nycschools.view.base.Presenter

interface SchoolListPresenter : Presenter<SchoolListView> {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel)
}
