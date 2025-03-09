package com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel

import androidx.lifecycle.LiveData
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.ComposeSchoolListUiModel
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem

interface ComposeSchoolListViewModel {
    val toasts : LiveData<String>
    fun getSchoolList(): LiveData<ComposeSchoolListUiModel>
    fun onSchoolListItemSelected(nycListItem: NycListItem)
    fun onLinkClicked(websiteLink: String)
}
