package com.example.thomasraybould.nycschools.features.school_list_compose_activity

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel.ComposeSchoolListViewModel
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem

@Preview
@Composable
fun SchoolListScreenPreview() {
    val satScoreData = SatScoreData.newBuilder()
        .math(0)
        .reading(400)
        .writing(800)
        .build()


    val boroughItemUiModel =
        NycListItem.BoroughItemUiModel(Borough.MANHATTAN, R.drawable.manhattan, false)

    val schoolItemUiModel1 = NycListItem.SchoolItemUiModel(
        borough = Borough.QUEENS,
        school = School("test1", "Test Highschool1", Borough.BRONX),
        isLoading = false,
        isSelected = false,
        satScoreData = satScoreData
    )

    val schoolItemUiModel2 = NycListItem.SchoolItemUiModel(
        borough = Borough.QUEENS,
        school = School("test2", "Test Highschool2", Borough.BRONX),
        isLoading = false,
        isSelected = false,
        satScoreData = satScoreData
    )


    val composeSchoolListUiModel = ComposeSchoolListUiModel(
        listOf(
            boroughItemUiModel,
            schoolItemUiModel1,
            schoolItemUiModel2
        )
    )

    val uiModel = remember { mutableStateOf(composeSchoolListUiModel) }

    SchoolListView(uiModel.value, {
        if (it !is NycListItem.SchoolItemUiModel) return@SchoolListView
        val index = uiModel.value.schoolListItemUiModels.indexOf(it)
        val newList = uiModel.value.schoolListItemUiModels.toMutableList().apply {
            set(index, it.copy(isSelected = !it.isSelected))
        }.toList()
        uiModel.value = ComposeSchoolListUiModel(newList)
    }, {})
}


@Composable
fun SchoolListScreen(
    schoolListViewModel: ComposeSchoolListViewModel
) {
    val state =
        schoolListViewModel.getSchoolList().observeAsState(ComposeSchoolListUiModel(emptyList()))
    SchoolListView(
        state.value,
        { nycListItem ->
            schoolListViewModel.onSchoolListItemSelected(nycListItem)
        },
        { schoolItemUiModel ->
            schoolItemUiModel.school.webPageLink?.let { websiteLink ->
                schoolListViewModel.onLinkClicked(websiteLink)
            }
        })
}


@Composable
fun SchoolListView(
    composeSchoolListUiModel: ComposeSchoolListUiModel,
    onNycListItemSelected: ((NycListItem) -> Unit),
    onLinkClicked: ((NycListItem.SchoolItemUiModel) -> Unit)
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn {
            items(composeSchoolListUiModel.schoolListItemUiModels) { nycListItem ->
                if (nycListItem is NycListItem.BoroughItemUiModel) {
                    BoroughItem(nycListItem, onNycListItemSelected)
                } else if (nycListItem is NycListItem.SchoolItemUiModel) {
                    SchoolItem(
                        nycListItem,
                        onNycListItemSelected,
                        { onLinkClicked.invoke(nycListItem) })
                }
            }
        }
    }

}