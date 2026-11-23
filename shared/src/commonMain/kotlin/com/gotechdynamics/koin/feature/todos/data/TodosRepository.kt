package com.gotechdynamics.koin.feature.todos.data

class TodosRepository(
    private val todosApi: TodosApi,
) {
    suspend fun getTodos(): List<Todo> =
        todosApi.getTodos()
    suspend fun getTodo(id: Long): Todo =
        todosApi.getTodo(id)
    suspend fun createTodo(
        title: String,
        userId: Long = 1L,
    ): Todo =
        todosApi.createTodo(
            TodoWriteRequest(
                userId = userId,
                title = title,
                completed = false,
            )
        )
    suspend fun updateTodo(todo: Todo): Todo =
        todosApi.updateTodo(
            id = todo.id,
            request = TodoWriteRequest(
                userId = todo.userId,
                title = todo.title,
                completed = todo.completed,
            )
        )

    suspend fun deleteTodo(id: Long) =
        todosApi.deleteTodo(id)
}