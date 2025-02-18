package com.example.thomasraybould.nycschools.view.school_list_activity.compose

import ListItemWithUnderline
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
                isLoading = true,
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
                isSelected = true
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
            Box(modifier = Modifier.height(50.dp)) {
                Text(
                    text = schoolItemUiModel.schoolName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(end = 75.dp)
                        .align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodyLarge,
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
        if (schoolItemUiModel.isSelected) {
            ScoreCard(schoolItemUiModel.satScoreData)
        }
    }
}

@Composable
fun ScoreCard(satScoreData: SatScoreData) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ScoreView("Math", satScoreData.math, Modifier.weight(1.0f))
            ScoreView("Reading", satScoreData.reading, Modifier.weight(1.0f))
            ScoreView("Writing", satScoreData.writing, Modifier.weight(1.0f))
        }
    }
}

@Composable
fun ScoreView(sectionName: String, score: Int, modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column {
            Text(
                sectionName,
                Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                "$score/800",
                Modifier.align(Alignment.CenterHorizontally)
            )
            ProgressBarRangeInfo
            LinearProgressIndicator(
                progress = { (score/800.0).toFloat() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}