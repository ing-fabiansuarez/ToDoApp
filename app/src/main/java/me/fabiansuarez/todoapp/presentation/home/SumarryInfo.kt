package me.fabiansuarez.todoapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SumarryInfo(
    modifier: Modifier = Modifier,
    date: String = "March 9, 2025",
    taskSummary: String = "5, incomplete, 5 completed"
) {
    Column {
        Text(
            text = date,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = taskSummary,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSummaryInfo() {
    MaterialTheme {
        SumarryInfo()
    }
}