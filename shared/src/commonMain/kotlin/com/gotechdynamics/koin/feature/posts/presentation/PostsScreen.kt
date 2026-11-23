package com.gotechdynamics.koin.feature.posts.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostsScreen(
    onPostClick: (Long) -> Unit,
    onCreateClick: () -> Unit,
    onTodosClick: () -> Unit,
) {
    val viewModel = koinViewModel<PostsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Button(onClick = onTodosClick) {
            Text("Buka TODO")
        }
        Button(onClick = onCreateClick){
            Text("Tambah post")
        }
        when {
            state.isLoading -> CircularProgressIndicator()

            state.error != null -> {
                Text("Gagal memuat post")
                Button(onClick = { viewModel.loadPosts()}) {
                    Text("Coba lagi")
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.posts, key = { it.id }) { post ->
                        Column(
                            modifier = Modifier
                                .clickable { onPostClick(post.id) }
                                .padding(8.dp)
                        ) {
                            Text(
                                text = post.title,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(post.body)
                        }
                    }
                }
            }

        }
    }

}
