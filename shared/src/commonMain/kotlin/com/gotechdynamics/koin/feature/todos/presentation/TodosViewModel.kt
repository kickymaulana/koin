package com.gotechdynamics.koin.feature.todos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotechdynamics.koin.feature.todos.data.Todo
import com.gotechdynamics.koin.feature.todos.data.TodosRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TodosState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class TodosViewModel(
    private val repository: TodosRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(TodosState())
    val state = _state.asStateFlow()

    init {
        loadTodos()
    }

    fun loadTodos() {
        if (_state.value.isLoading) return

        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val todos = repository.getTodos()
                _state.value = TodosState(todos = todos )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Gagal memuat TODO. Coba lagi."
                )
            }
        }
    }

    fun updateTodoInList(updatedTodo: Todo) {
        _state.value = _state.value.copy(
            todos = _state.value.todos.map { todo ->
                if (todo.id == updatedTodo.id ) updatedTodo else todo
            }
        )
    }

    fun removeTodoFromList(id: Long) {
        _state.value = _state.value.copy(
            todos = _state.value.todos.filterNot { it.id == id },
        )
    }

    fun addTodoToList(todo: Todo) {
        _state.value = _state.value.copy(todos = listOf(todo ) + _state.value.todos)
    }
}
