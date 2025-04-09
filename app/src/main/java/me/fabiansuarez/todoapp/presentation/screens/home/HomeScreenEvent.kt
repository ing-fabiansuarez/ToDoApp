package me.fabiansuarez.todoapp.presentation.screens.home

sealed class HomeScreenEvent {
    data object UpdatedTasks: HomeScreenEvent()
    data object DeletedAllTask: HomeScreenEvent()
    data object DeletedTask: HomeScreenEvent()
}