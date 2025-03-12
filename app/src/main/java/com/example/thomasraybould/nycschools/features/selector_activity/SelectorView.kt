package com.example.thomasraybould.nycschools.features.selector_activity

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.thomasraybould.nycschools.R

@Preview
@Composable
private fun SelectorViewPreview() {
    val selectableItems = listOf(
        SelectableItem(R.string.rx_constraint_layout, {}),
        SelectableItem(R.string.rx_compose, {}),
    )
    SelectorViewScreen(SelectorActivityUiModel(selectableItems))
}

@Composable
fun SelectorViewScreen(selectorActivityUiModel: SelectorActivityUiModel) {
    SelectorViewList(selectorActivityUiModel)
}

@Composable
fun SelectorViewList(selectorActivityUiModel: SelectorActivityUiModel) {
    LazyColumn {
        items(selectorActivityUiModel.selectableItems) { item ->
            SelectableItem(item)
        }
    }
}

@Composable
fun SelectableItem(selectableItem: SelectableItem) {
    Button(selectableItem.onItemSelected) {
        Text(stringResource(selectableItem.activityName))
    }
}

