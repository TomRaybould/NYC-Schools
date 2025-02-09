package com.example.thomasraybould.nycschools.view.school_list_activity

import ListItemWithUnderline
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

@Preview
@Composable
fun SchoolPreview() {
    Column {
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                schoolName = "Test school not expanded",
                satScoreData = SatScoreData.newBuilder()
                    .math(500)
                    .reading(501)
                    .writing(502)
                    .build(),
                isLoading = false,
                isSelected = false
            )
        )
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                schoolName = "Test school long text not expanded, loading state",
                satScoreData = SatScoreData.newBuilder()
                    .math(500)
                    .reading(501)
                    .writing(502)
                    .build(),
                isLoading = false,
                isSelected = false
            )
        )
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                schoolName = "Test school expanded",
                satScoreData = SatScoreData.newBuilder()
                    .math(500)
                    .reading(501)
                    .writing(502)
                    .build(),
                isLoading = false,
                isSelected = false
            )
        )
    }
}

@Composable
fun SchoolItem(schoolItemUiModel: NycListItem.SchoolItemUiModel) {
    SchoolItemContent(schoolItemUiModel)
}

@Composable
fun SchoolItemContent(schoolItemUiModel: NycListItem.SchoolItemUiModel) {
    Column {
        ListItemWithUnderline {
            Box {
                Text(
                    text = schoolItemUiModel.schoolName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .padding(end = 75.dp)
                        .align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}