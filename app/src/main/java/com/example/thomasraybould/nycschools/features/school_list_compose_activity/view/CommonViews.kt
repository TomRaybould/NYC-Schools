package com.example.thomasraybould.nycschools.features.school_list_compose_activity.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItemWithUnderline(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Surface(modifier = modifier) {
        Column {
            content()
            HorizontalDivider(Modifier.padding(start = 4.dp), thickness = 1.dp)
        }
    }
}