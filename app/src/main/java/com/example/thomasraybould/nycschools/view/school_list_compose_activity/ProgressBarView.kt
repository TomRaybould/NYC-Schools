package com.example.thomasraybould.nycschools.view.school_list_compose_activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun ProgressBarPreview() {
    Surface(Modifier.background(Color.Cyan)){
        ProgressBarContainer(percentage = .50f)
    }
}

@Composable
fun ProgressBarContainer(
    modifier: Modifier = Modifier,
    percentage: Float = .50f,
    primaryColor: Color = Color.DarkGray,
    secondaryColor: Color = Color.Gray
) {
    Surface(modifier.background(Color.Transparent)) {
        ProgressBar(
            percentage = percentage,
            primaryColor = primaryColor,
            secondaryColor = secondaryColor
        )
    }
}

@Composable
private fun ProgressBar(
    percentage: Float = 1.0f,
    primaryColor: Color = Color.DarkGray,
    secondaryColor: Color = Color.Gray
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        RoundedLine(percentage = 1.0f, color = secondaryColor)
        RoundedLine(percentage = percentage, color = primaryColor)
    }
}

@Composable
private fun RoundedLine(
    percentage: Float = .50f,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(percentage)
            .height(10.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(50) // Apply rounded corners to the line
            )
            .padding(horizontal = 16.dp) // Optional: Add padding to simulate a start/end space
    )
}