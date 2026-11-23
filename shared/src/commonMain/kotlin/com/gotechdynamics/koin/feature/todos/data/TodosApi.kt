package com.gotechdynamics.koin.feature.todos.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

@Serializable
data class TodoWriteRequest(
    val userId: Long,
    val title: String,
    val completed: Boolean,
)

class TodosApi (
    private val client: HttpClient,
) {
    private val baseUrl = "http://jsonplaceholder.typicode.com/todos"

    suspend fun getTodos(): List<Todo> =
        client.get(baseUrl) {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun getTodo(id: Long): Todo =
        client.get("$baseUrl/$id") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun createTodo(request: TodoWriteRequest): Todo =
        client.post(baseUrl) {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun updateTodo(id: Long, request: TodoWriteRequest): Todo =
        client.put("$baseUrl/$id") {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    suspend fun deleteTodo(id: Long) {
        client.delete("$baseUrl/$id")
    }
}
