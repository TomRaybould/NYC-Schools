package com.example.thomasraybould.nycschools.features.selector_activity

import androidx.annotation.StringRes

data class SelectorActivityUiModel(val selectableItems: List<SelectableItem>)

data class SelectableItem(
    @StringRes
    val activityName: Int,
    val onItemSelected: () -> Unit
)
