package com.gotechdynamics.koin.feature.posts.data

class PostsRepository(
    private val postsApi: PostsApi,
) {
    suspend fun getPosts(): List<Post> = postsApi.getPosts()

    suspend fun getPost(postId: Long): Post = postsApi.getPost(postId)

    suspend fun updatePost(post: Post): Post = postsApi.updatePost(post)

    suspend fun createPost(
        title: String,
        body: String,
    ): Post {
        return postsApi.createPost(
            CreatePostRequest(
                userId = 1L,
                title = title,
                body= body,
            )
        )
    }
}