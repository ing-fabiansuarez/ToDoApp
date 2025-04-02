package me.fabiansuarez.todoapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.fabiansuarez.todoapp.domain.Task
import me.fabiansuarez.todoapp.domain.TaskLocalDataSource

class FakeTaskLocalDataSource : TaskLocalDataSource {

    private val _taskFlow = MutableStateFlow<List<Task>>(emptyList())

    override val taskFlow: Flow<List<Task>>
        get() = _taskFlow

    override suspend fun addTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        tasks.add(task)
        delay(5000)
        _taskFlow.value = tasks
    }

    override suspend fun updateTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        val taskIndex = tasks.indexOfFirst { it.id == task.id }
        if (taskIndex != -1) {
            tasks[taskIndex] = task
            delay(5000)
        }
        _taskFlow.value = tasks
    }

    override suspend fun removeTask(task: Task) {
        val tasks = _taskFlow.value.toMutableList()
        tasks.remove(task)
        delay(5000)
        _taskFlow.value = tasks
    }

    override suspend fun deleteAllTask() {
        _taskFlow.value = emptyList()
    }

    override suspend fun getTaskById(id: String): Task? {
        return _taskFlow.value.firstOrNull {
            it.id == id
        }
    }
}