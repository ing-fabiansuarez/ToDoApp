package me.fabiansuarez.todoapp.presentation.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SumarryInfo(
    modifier: Modifier = Modifier,
    date: String = "March 9, 2025",
    taskSummary: String = "5, incomplete, 5 completed",
    completedTasks: Int,
    totalTask: Int
) {
    val angleRatio = remember {
        Animatable(0f)
    }
    LaunchedEffect(completedTasks, totalTask) {
        if (totalTask == 0) {
            return@LaunchedEffect
        }
        if (totalTask > 0) {
            angleRatio.animateTo(
                targetValue = (completedTasks / totalTask.toFloat()),
                animationSpec = tween(
                    durationMillis = 2000
                )
            )
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column (
            modifier = Modifier.weight(1.5f)
        ) {
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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(16.dp)
                .aspectRatio(1f)
                .weight(1f)
        ) {
            val colorBase = MaterialTheme.colorScheme.inversePrimary
            val progress = MaterialTheme.colorScheme.primary
            val strokeWith = 16.dp
            Canvas(
                modifier = Modifier
                    .aspectRatio(1f)
            ) {
                drawArc(
                    color = colorBase,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWith.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                if (completedTasks <= totalTask) {
                    drawArc(
                        color = progress,
                        startAngle = 90f,
                        sweepAngle = ((360 * angleRatio.value)),
                        useCenter = false,
                        size = size,
                        style = Stroke(
                            width = strokeWith.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }

            }
            Text(
                text = "${(completedTasks / totalTask.toFloat()).times(100).toInt()}%",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSummaryInfo() {
    MaterialTheme {
        SumarryInfo(
            completedTasks = 5, totalTask = 10
        )
    }
}