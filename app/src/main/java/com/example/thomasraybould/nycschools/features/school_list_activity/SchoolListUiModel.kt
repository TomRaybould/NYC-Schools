package com.example.thomasraybould.nycschools.features.school_list_activity

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel

data class SchoolListUiModel(val schoolListItemUiModels: List<SchoolListItemUiModel>, val errorMessage: String? = null)