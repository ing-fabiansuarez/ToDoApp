@file:OptIn(ExperimentalFoundationApi::class)

package me.fabiansuarez.todoapp.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.fabiansuarez.todoapp.presentation.screens.home.components.SectionTitle
import me.fabiansuarez.todoapp.presentation.screens.home.components.SumarryInfo
import me.fabiansuarez.todoapp.presentation.screens.home.components.TaskItem
import me.fabiansuarez.todoapp.presentation.screens.home.providers.HomeScreenPreviewProvider
import me.fabiansuarez.todoapp.ui.theme.ToDoAppTheme


@Composable
fun HomeScreenRoot() {
    val viewModel = viewModel<HomeScreenViewModel>()

    val state = viewModel.state
    val event = viewModel.event
    val context = LocalContext.current

    LaunchedEffect(true) {
        event.collect { event ->
            when (event) {
                HomeScreenEvent.DeletedAllTask -> {
                    Toast.makeText(context, "All tasks deleted", Toast.LENGTH_SHORT).show()
                }

                HomeScreenEvent.DeletedTask -> {
                    Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                }

                HomeScreenEvent.UpdatedTasks -> {
                    Toast.makeText(context, "Tasks updated", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeDataState,
    onAction: (HomeScreenAction) -> Unit
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "App name",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            isMenuExpanded = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Add Task",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    onAction(HomeScreenAction.OnDeleteAllTask)
                                    isMenuExpanded = false
                                },
                                text = {
                                    Text(text = "Delete All Task")
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                SumarryInfo(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    date = state.date,
                    taskSummary = state.summary,
                    completedTasks = state.completedTask.size,
                    totalTask = state.completedTask.size + state.pendingTask.size,
                )
            }
            stickyHeader {
                SectionTitle(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        ),
                    title = "Tareas Completadas",

                    )
            }
            items(state.completedTask, key = { task -> task.id }) { task ->
                TaskItem(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    task = task,
                    onDeleteItem = {
                        onAction(HomeScreenAction.OnDeleteTask(task))
                    },
                    onClickItem = {},
                    onToggleCompletion = {
                        onAction(HomeScreenAction.OnToggleTask(task))
                    }
                )
            }

            stickyHeader {
                SectionTitle(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        ),
                    title = "Tareas No Completed"
                )
            }
            items(state.pendingTask, key = { task -> task.id }) { task ->
                TaskItem(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    task = task,
                    onDeleteItem = {
                        onAction(HomeScreenAction.OnDeleteTask(task))
                    },
                    onClickItem = {},
                    onToggleCompletion = {
                        onAction(HomeScreenAction.OnToggleTask(task))
                    }
                )
            }

        }


    }

}


@Preview
@Composable
fun HomeScreenPreviewLight(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeDataState
) {
    ToDoAppTheme {
        HomeScreen(
            state = HomeDataState(
                date = state.date,
                summary = state.summary,
                completedTask = state.completedTask,
                pendingTask = state.pendingTask
            ),
            onAction = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreviewDark(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeDataState
) {
    ToDoAppTheme {
        HomeScreen(
            state = HomeDataState(
                date = state.date,
                summary = state.summary,
                completedTask = state.completedTask,
                pendingTask = state.pendingTask
            ),
            onAction = {}
        )
    }
}



