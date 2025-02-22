package com.example.thomasraybould.nycschools.features.school_list_activity

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel

interface SchoolListViewModel {
    fun getSchoolList(): LiveData<SchoolListUiModel>
    fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel)
}
