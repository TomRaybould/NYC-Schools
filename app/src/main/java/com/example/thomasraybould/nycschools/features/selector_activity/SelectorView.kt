package com.example.thomasraybould.nycschools.features.selector_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thomasraybould.nycschools.R

@Preview
@Composable
private fun SelectorViewPreview() {
    val selectableItems = listOf(
        SelectableItem(R.string.rx_constraint_layout, {}),
        SelectableItem(R.string.rx_compose, {}),
    )
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        SelectorViewScreen(SelectorActivityUiModel(selectableItems))
    }
}

@Composable
fun SelectorViewScreen(selectorActivityUiModel: SelectorActivityUiModel) {
    SelectorViewList(selectorActivityUiModel)
}

@Composable
fun SelectorViewList(selectorActivityUiModel: SelectorActivityUiModel) {
    Column {
        Text(
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.titleLarge,
            text = (stringResource(R.string.selector_activity_title))
        )
        Text(
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.bodyMedium,
            text = (stringResource(R.string.selector_activity_subtitle)))
        LazyColumn {
            items(selectorActivityUiModel.selectableItems) { item ->
                SelectableItem(item)
            }
        }
    }
}

@Composable
fun SelectableItem(selectableItem: SelectableItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = selectableItem.onItemSelected
        ) {
            Text(stringResource(selectableItem.activityName))
        }
    }
}

