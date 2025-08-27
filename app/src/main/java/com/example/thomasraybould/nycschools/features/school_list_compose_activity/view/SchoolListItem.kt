package com.example.thomasraybould.nycschools.features.school_list_compose_activity.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem

@Preview
@Composable
fun SchoolPreview() {

    val satScoreData = SatScoreData(
        dbn = "",
        dataAvailable = true,
        math = 300,
        reading = 400,
        writing = 500
    )

    val schoolItemUiModels = mutableListOf(
        NycListItem.SchoolItemUiModel(
            borough = Borough.QUEENS,
            school = School("", "Test school not expanded", Borough.QUEENS),
            isLoading = false,
            isSelected = false,
            satScoreData = satScoreData
        ),
        NycListItem.SchoolItemUiModel(
            borough = Borough.QUEENS,
            school = School(
                "",
                "Test school long text not expanded, loading state",
                Borough.QUEENS
            ),
            isLoading = true,
            isSelected = false,
            satScoreData = satScoreData
        ),
        NycListItem.SchoolItemUiModel(
            borough = Borough.QUEENS,
            school = School(
                "",
                "Test school long text not expanded, not loading state",
                Borough.QUEENS
            ),
            isLoading = false,
            isSelected = false,
            satScoreData = satScoreData
        ),
        NycListItem.SchoolItemUiModel(
            borough = Borough.QUEENS,
            school = School("", "Test school expanded", Borough.QUEENS),
            isLoading = false,
            isSelected = true,
            satScoreData = satScoreData
        )
    )

    Column {
        schoolItemUiModels.forEach {
            SchoolItem(it, onNycListItemSelected = { schoolModel ->
                if (schoolModel !is NycListItem.SchoolItemUiModel) return@SchoolItem
                val index = schoolItemUiModels.indexOf(schoolModel)
                schoolItemUiModels[index] = schoolModel.copy(isSelected = schoolModel.isSelected)
            })
        }
    }
}

@Composable
fun SchoolItem(
    schoolItemUiModel: NycListItem.SchoolItemUiModel,
    onNycListItemSelected: ((NycListItem) -> Unit)? = null,
    onLinkClicked: (() -> Unit)? = null
) {
    SchoolItemContent(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onNycListItemSelected?.invoke(schoolItemUiModel)
            },
        schoolItemUiModel = schoolItemUiModel,
        onLinkClicked = onLinkClicked
    )
}

@Composable
fun SchoolItemContent(
    modifier: Modifier = Modifier,
    schoolItemUiModel: NycListItem.SchoolItemUiModel,
    onLinkClicked: (() -> Unit)? = null
) {
    Surface(modifier = modifier.animateContentSize()) {
        Column {
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
            if (schoolItemUiModel.isSelected && schoolItemUiModel.satScoreData != null) {
                ScoreCard(
                    satScoreData = schoolItemUiModel.satScoreData,
                    websiteLink = schoolItemUiModel.school.webPageLink,
                    onLinkClicked = { onLinkClicked?.invoke() }
                )
            }
        }
    }
}
