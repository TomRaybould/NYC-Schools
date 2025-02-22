package com.example.thomasraybould.nycschools.view.school_list_compose_activity.viewModel

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListUiModel
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

interface ComposeSchoolListViewModel {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(nycListItem: NycListItem)
}
