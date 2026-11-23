package com.gotechdynamics.koin.feature.posts.data

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val userId: Long,
    val title: String,
    val body: String,
)