package com.example.thomasraybould.nycschools.view.school_list_compose_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

@Composable
fun SchoolListView(nycListItems: List<NycListItem>) {
    Column {
        nycListItems.map { item ->
            if (item is NycListItem.BoroughItemUiModel) {
                BoroughItem(item)
            } else if (item is NycListItem.SchoolItemUiModel) {
                SchoolItem(item)
            }
        }
    }
}