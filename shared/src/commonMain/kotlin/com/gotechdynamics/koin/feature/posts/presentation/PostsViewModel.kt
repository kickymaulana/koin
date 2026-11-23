package com.gotechdynamics.koin.feature.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gotechdynamics.koin.feature.posts.data.Post
import com.gotechdynamics.koin.feature.posts.data.PostsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PostsState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class PostsViewModel(
    private val repository: PostsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        if (_state.value.isLoading) return
        _state.value = _state.value.copy(
            isLoading = true,
            error = null,
        )

        viewModelScope.launch {
            try {
                val posts = repository.getPosts()
                _state.value = PostsState(posts = posts)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Gagal memuat post. Coba lagi.",
                )
            }

        }
    }

    fun updatePost(post: Post) {
        _state.value = _state.value.copy(
            posts = _state.value.posts.map {
                if (it.id == post.id) post else it
            }
        )
    }
}
