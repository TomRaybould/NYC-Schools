package com.example.thomasraybould.nycschools.features.school_list_compose_activity.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.features.errorColor
import com.example.thomasraybould.nycschools.features.linkColor
import com.example.thomasraybould.nycschools.features.neutralColor
import com.example.thomasraybould.nycschools.features.successColor

@Preview
@Composable
fun ScoreCardPreview() {
    Column {

        val satScoreData1 = SatScoreData(
            dbn = "",
            dataAvailable = true,
            math = 300,
            reading = 400,
            writing = 500
        )
        val satScoreData2 = SatScoreData(
            dbn = "",
            dataAvailable = true,
            math = 300,
            reading = 400,
            writing = 500
        )


        ScoreCard(satScoreData1)
        ScoreCard(satScoreData2, "www.test.com")

    }
}

@Composable
fun ScoreCard(
    satScoreData: SatScoreData,
    websiteLink: String? = null,
    onLinkClicked: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 4.dp),
    ) {
        Column(modifier = Modifier.wrapContentHeight()) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val scoreViewModifier = Modifier.weight(1.0f)
                ScoreView("Math", satScoreData.math, scoreViewModifier)
                ScoreView("Reading", satScoreData.reading, scoreViewModifier)
                ScoreView("Writing", satScoreData.writing, scoreViewModifier)
            }
            if (websiteLink != null) {
                Text(
                    text = stringResource(R.string.visit_website),
                    color = linkColor,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(4.dp)
                        .clickable {
                            onLinkClicked?.invoke()
                        }
                )
            }
        }
    }
}

@Composable
fun ScoreView(
    sectionName: String,
    score: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            sectionName,
            Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentHeight()
                .padding(top = 4.dp)
        )
        Text(
            if (score >= 0) {
                "$score/800"
            } else {
                stringResource(R.string.no_score_available)
            },
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        ProgressBarContainer(
            modifier = Modifier
                .padding(10.dp),
            percentage = (score / 800.0).toFloat(),
            primaryColor = getProgressColor(score),
            secondaryColor = Color.Gray
        )
    }
}

private fun getProgressColor(score: Int): Color {
    return if (score <= 350) {
        errorColor
    } else if (score <= 450) {
        neutralColor
    } else {
        successColor
    }
}