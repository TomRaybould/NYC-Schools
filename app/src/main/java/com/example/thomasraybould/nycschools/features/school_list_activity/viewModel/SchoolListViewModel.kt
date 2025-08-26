package com.example.thomasraybould.nycschools.features.school_list_activity.viewModel

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import com.example.thomasraybould.nycschools.features.school_list_activity.SchoolListUiModel

interface SchoolListViewModel {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel)
}