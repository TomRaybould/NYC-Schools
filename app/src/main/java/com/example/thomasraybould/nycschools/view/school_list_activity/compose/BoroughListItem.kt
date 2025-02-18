package com.example.thomasraybould.nycschools.view.school_list_activity.compose

import ListItemWithUnderline
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem

fun getTestBoroughs(isLoading: Boolean): List<NycListItem.BoroughItemUiModel> {
    return listOf(
        NycListItem.BoroughItemUiModel(Borough.MANHATTAN, R.drawable.manhattan, isLoading),
        NycListItem.BoroughItemUiModel(Borough.BROOKLYN, R.drawable.brooklyn, isLoading),
        NycListItem.BoroughItemUiModel(Borough.QUEENS, R.drawable.queens, isLoading),
        NycListItem.BoroughItemUiModel(Borough.STATEN_ISLAND, R.drawable.statenisland, isLoading),
        NycListItem.BoroughItemUiModel(Borough.BRONX, R.drawable.bronx, isLoading),
    )
}

@Preview
@Composable
fun Preview() {
    Column {
        getTestBoroughs(false).map { BoroughItem(it) }
        getTestBoroughs(true).map { BoroughItem(it) }
    }
}

@Composable
fun BoroughItem(boroughItemUiModel: NycListItem.BoroughItemUiModel) {
    ListItemWithUnderline {
        BoroughContent(boroughItemUiModel)
    }
}


@Composable
fun BoroughContent(boroughItemUiModel: NycListItem.BoroughItemUiModel) {
    Box {
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(4.dp)
        ) {

            val boroughImagePainter = painterResource(id = boroughItemUiModel.imageRes)

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
                    .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                painter = boroughImagePainter,
                contentDescription = "test"
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 6.dp),
                text = boroughItemUiModel.borough.boroughTitle,
                style = MaterialTheme.typography.headlineMedium
            )

        }
        if (boroughItemUiModel.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .padding(end = 4.dp),
            )
        }
    }
}
