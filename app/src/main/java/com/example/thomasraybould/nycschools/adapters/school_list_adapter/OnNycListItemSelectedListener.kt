package com.example.thomasraybould.nycschools.adapters.school_list_adapter

import com.example.thomasraybould.nycschools.features.uiModels.NycListItem

interface OnNycListItemSelectedListener {
    fun onNycListItemSelected(schoolItemUiModel: NycListItem)
}
