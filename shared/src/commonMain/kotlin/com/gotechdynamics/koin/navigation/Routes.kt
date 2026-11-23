package com.gotechdynamics.koin.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object PostsRoute : NavKey

@Serializable
data class EditPostRoute(val postId: Long) : NavKey

@Serializable
data object CreatePostRoute : NavKey

@Serializable
data object TodosRoute : NavKey
