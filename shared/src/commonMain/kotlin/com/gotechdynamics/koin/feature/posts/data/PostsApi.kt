package com.gotechdynamics.koin.feature.posts.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType

class PostsApi(
    private val client: HttpClient,
) {
    suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts") {
            accept(ContentType.Application.Json)
        }.body()
    }

    suspend fun getPost(postId: Long): Post {
        require(postId > 0) { "postId harus lebih dari 0"}

        return client.get("https://jsonplaceholder.typicode.com/posts/$postId") {
            accept(ContentType.Application.Json)
        }.body()
    }

    suspend fun updatePost(post: Post): Post {
        return client.put("https://jsonplaceholder.typicode.com/posts/${post.id}") {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()
    }

    suspend fun createPost(request: CreatePostRequest): Post {
        return client.post("https://jsonplaceholder.typicode.com/posts") {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
