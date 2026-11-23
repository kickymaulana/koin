package com.gotechdynamics.koin.feature.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotechdynamics.koin.feature.posts.data.Post
import com.gotechdynamics.koin.feature.posts.data.PostsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreatePostState(
    val isSaving: Boolean = false,
    val createdPost: Post? = null,
    val error: String? = null,
)

class CreatePostViewModel(
    private val repository: PostsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CreatePostState())
    val state = _state.asStateFlow()

    fun createPost(title: String, body: String) {
        val cleanTitle = title.trim()
        val cleanBody = body.trim()

        if (cleanTitle.isBlank() || cleanBody.isBlank() || _state.value.isSaving) {
            return
        }

        viewModelScope.launch {
            _state.value = CreatePostState(isSaving = true)
            try {
                val post = repository.createPost(
                    title = cleanTitle,
                    body = cleanBody,
                )
                _state.value = CreatePostState(createdPost = post)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = CreatePostState(
                    error = "Gagal menambahkan post. Coba lagi.",
                )
            }
        }
    }
}
