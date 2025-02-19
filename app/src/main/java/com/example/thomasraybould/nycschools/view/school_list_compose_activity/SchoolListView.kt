package com.example.thomasraybould.nycschools.view.school_list_compose_activity

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.pm.ShortcutInfoCompat.Surface
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListUiModel
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListViewModel
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

@Preview
@Composable
fun SchoolListScreenPreview() {
    val boroughItemUiModel =
        NycListItem.BoroughItemUiModel(Borough.MANHATTAN, R.drawable.manhattan, false)

    val schoolItemUiModel1 = NycListItem.SchoolItemUiModel(
        borough = Borough.QUEENS,
        school = School.newBuilder().name("Test Highschool").build(),
        isLoading = false,
        isSelected = false
    )

    val schoolItemUiModel2 = NycListItem.SchoolItemUiModel(
        borough = Borough.QUEENS,
        school = School.newBuilder()
            .name("Test Highschool").build(),
        isLoading = false,
        isSelected = false
    )

    val satScoreData =
        NycListItem.SatScoreDataUiModel(
            Borough.BROOKLYN,
            SatScoreData.newBuilder()
                .math(0)
                .reading(400)
                .writing(800)
                .build(),
            ""
        )

    val schoolListUiModel = SchoolListUiModel(
        listOf(
            boroughItemUiModel,
            schoolItemUiModel1,
            satScoreData,
            schoolItemUiModel2

            )
    )

    SchoolListView(schoolListUiModel)
}


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
    onNycListItemSelected: ((NycListItem) -> Unit)? = null
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn {
            items(schoolListUiModel.schoolListItemUiModels) { nycListItem ->
                if (nycListItem is NycListItem.BoroughItemUiModel) {
                    BoroughItem(nycListItem, onNycListItemSelected)
                } else if (nycListItem is NycListItem.SchoolItemUiModel) {
                    SchoolItem(nycListItem, onNycListItemSelected)
                } else if (nycListItem is NycListItem.SatScoreDataUiModel) {
                    ScoreCard(nycListItem)
                }
            }
        }
    }

}