package com.example.thomasraybould.nycschools.view.school_list_activity

import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

data class SchoolListUiModel(
    val schoolListItemUiModels: List<NycListItem>,
    val errorMessage: String? = null
)