package me.fabiansuarez.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.fabiansuarez.todoapp.data.FakeTaskLocalDataSource
import me.fabiansuarez.todoapp.domain.Task
import me.fabiansuarez.todoapp.ui.theme.ToDoAppTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                var text by remember { mutableStateOf("") }
                val fakeTaskLocalDataSource = FakeTaskLocalDataSource()

                LaunchedEffect(true) {
                    fakeTaskLocalDataSource.taskFlow.collect {
                        text = it.toString()
                    }

                }

                LaunchedEffect(true) {
                    fakeTaskLocalDataSource.addTask(
                        Task(
                            id = UUID.randomUUID().toString(),
                            title = "Task 1",
                            description = "Description 1"
                        )
                    )
                    fakeTaskLocalDataSource.addTask(
                        Task(
                            id = UUID.randomUUID().toString(),
                            title = "Task 1",
                            description = "Description 1"
                        )
                    )
                }

                Scaffold { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Text(text = text)
                    }
                }


            }
        }
    }
}

