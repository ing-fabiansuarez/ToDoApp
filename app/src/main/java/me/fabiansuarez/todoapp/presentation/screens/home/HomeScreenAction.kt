package me.fabiansuarez.todoapp.presentation.screens.home

import me.fabiansuarez.todoapp.domain.Task

sealed interface HomeScreenAction {
    data class OnToggleTask(val task: Task) : HomeScreenAction
    data class OnDeleteTask(val task: Task) : HomeScreenAction
    data object OnDeleteAllTask : HomeScreenAction
    data object OnAddTask : HomeScreenAction
}