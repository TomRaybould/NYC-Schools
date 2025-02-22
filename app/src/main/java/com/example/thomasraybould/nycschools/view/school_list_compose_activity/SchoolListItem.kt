package com.example.thomasraybould.nycschools.view.school_list_compose_activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

@Preview
@Composable
fun SchoolPreview() {
    Column {
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                borough = Borough.QUEENS,
                school = School.newBuilder().name("Test school not expanded").build(),
                isLoading = false,
                isSelected = false
            )
        )
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                borough = Borough.QUEENS,
                school = School.newBuilder()
                    .name("Test school long text not expanded, loading state").build(),
                isLoading = true,
                isSelected = false
            )
        )
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                borough = Borough.QUEENS,
                school = School.newBuilder()
                    .name("Test school long text not expanded, not loading state").build(),
                isLoading = false,
                isSelected = false
            )
        )
        SchoolItem(
            NycListItem.SchoolItemUiModel(
                borough = Borough.QUEENS,
                school = School.newBuilder().name("Test school expanded").build(),
                isLoading = false,
                isSelected = true
            )
        )


        val satScoreData =
            NycListItem.SatScoreDataUiModel(
                Borough.BROOKLYN,
                SatScoreData.newBuilder()
                    .math(300)
                    .reading(400)
                    .writing(500)
                    .build(),
                ""
            )

        ScoreCard(satScoreData)

    }
}

@Composable
fun SchoolItem(
    schoolItemUiModel: NycListItem.SchoolItemUiModel,
    onNycListItemSelected: ((NycListItem) -> Unit)? = null
) {
    SchoolItemContent(modifier = Modifier.clickable {
        onNycListItemSelected?.invoke(schoolItemUiModel)
    }, schoolItemUiModel = schoolItemUiModel)
}

@Composable
fun SchoolItemContent(
    modifier: Modifier = Modifier,
    schoolItemUiModel: NycListItem.SchoolItemUiModel
) {
    Surface(modifier = modifier) {
        ListItemWithUnderline {
            Box(modifier = Modifier.height(50.dp)) {
                Text(
                    text = schoolItemUiModel.school.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(end = if (schoolItemUiModel.isLoading) 50.dp else 4.dp)
                        .align(Alignment.CenterStart),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (schoolItemUiModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
}
