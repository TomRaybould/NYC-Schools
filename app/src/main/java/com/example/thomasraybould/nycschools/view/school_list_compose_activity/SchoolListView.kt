package com.example.thomasraybould.nycschools.view.school_list_compose_activity

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListUiModel
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListViewModel
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

@Composable
fun SchoolListScreen(
    schoolListViewModel: SchoolListViewModel
) {
    val state = schoolListViewModel.getSchoolList().observeAsState(SchoolListUiModel(emptyList()))
    SchoolListView(state.value) { schoolListViewModel.onSchoolListItemSelected(it) }
}


@Composable
fun SchoolListView(
    schoolListUiModel: SchoolListUiModel,
    onNycListItemSelected: (NycListItem) -> Unit
) {

    LazyColumn {
        items(schoolListUiModel.schoolListItemUiModels) { nycListItem ->
            if (nycListItem is NycListItem.BoroughItemUiModel) {
                BoroughItem(nycListItem, onNycListItemSelected)
            } else if (nycListItem is NycListItem.SchoolItemUiModel) {
                SchoolItem(nycListItem, onNycListItemSelected)
            }
        }
    }

}