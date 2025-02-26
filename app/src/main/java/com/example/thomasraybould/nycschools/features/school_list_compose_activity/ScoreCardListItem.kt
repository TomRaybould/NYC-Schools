package com.example.thomasraybould.nycschools.features.school_list_compose_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.features.errorColor
import com.example.thomasraybould.nycschools.features.neutralColor
import com.example.thomasraybould.nycschools.features.successColor
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem

@Preview
@Composable
fun ScoreCardPreview() {
    Column {

        val satScoreData1 = SatScoreData.newBuilder()
            .math(0)
            .reading(400)
            .writing(800)
            .build()
        val satScoreData2 = SatScoreData.newBuilder()
            .math(100)
            .reading(400)
            .writing(800)
            .build()


        ScoreCard(satScoreData1)
        ScoreCard(satScoreData2)

    }
}

@Composable
fun ScoreCard(satScoreData: SatScoreData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            val scoreViewModifier = Modifier.weight(1.0f)
            ScoreView("Math", satScoreData.math, scoreViewModifier)
            ScoreView("Reading", satScoreData.reading, scoreViewModifier)
            ScoreView("Writing", satScoreData.writing, scoreViewModifier)
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
                .padding(top = 4.dp)
        )
        Text(
            "$score/800",
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        ProgressBarContainer(
            modifier = Modifier
                .padding(10.dp)
                .weight(1.0f),
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