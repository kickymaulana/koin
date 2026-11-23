package com.gotechdynamics.koin.feature.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotechdynamics.koin.feature.posts.data.Post
import com.gotechdynamics.koin.feature.posts.data.PostsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class EditPostState(
    val post: Post? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val savedPost: Post? = null,
)

class EditPostViewModel(
    private val repository: PostsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(EditPostState())
    val state = _state.asStateFlow()

    fun loadPost(postId: Long) {
        if (_state.value.isLoading) return

        _state.value = EditPostState(isLoading = true)

        viewModelScope.launch {
            try {
                val post = repository.getPost(postId)
                _state.value = EditPostState(post = post)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = EditPostState(
                    error = "Gagal memuat post. Coba lagi."
                )
            }
        }
    }
    fun savePost(postId: Long, title: String, body: String) {
        val currentPost = _state.value.post ?: return
        val newTitle = title.trim()
        val newBody = body.trim()

        if (newTitle.isBlank() || newBody.isBlank() || _state.value.isSaving) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, saveError = null)

            try {
                val updatedPost = repository.updatePost(
                    currentPost.copy(
                        id = postId,
                        title = newTitle,
                        body = newBody,
                    )
                )
                _state.value = _state.value.copy(
                    post = updatedPost,
                    isSaving = false,
                    savedPost = updatedPost,
                )
            } catch (e: CancellationException){
                throw e
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSaving = false,
                    saveError = "Gagal menyimpan post. Coba lagi",
                )
            }
        }
    }
}