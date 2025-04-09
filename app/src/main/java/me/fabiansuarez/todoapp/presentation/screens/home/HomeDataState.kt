package me.fabiansuarez.todoapp.presentation.screens.home

import me.fabiansuarez.todoapp.domain.Task

data class HomeDataState(
    val date: String = "",
    val summary: String = "",
    val completedTask: List<Task> = emptyList(),
    val pendingTask: List<Task> = emptyList()
)