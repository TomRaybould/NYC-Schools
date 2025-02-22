package com.example.thomasraybould.nycschools.view.school_list_activity

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import com.example.thomasraybould.nycschools.view.school_list_compose_activity.ComposeSchoolListUiModel

interface SchoolListViewModel {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel)
}
