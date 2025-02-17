package com.example.thomasraybould.nycschools.view.school_list_activity

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

interface SchoolListViewModel {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(nycListItem: NycListItem)
}
