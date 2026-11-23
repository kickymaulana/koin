package com.gotechdynamics.koin.feature.posts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gotechdynamics.koin.feature.posts.data.Post
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EditPostScreen(
    postId: Long,
    onBack: () -> Unit,
    onSaved: (Post) -> Unit,
    viewModel: EditPostViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var title by remember(postId) { mutableStateOf("") }
    var body by remember(postId) { mutableStateOf("") }

    LaunchedEffect(postId) {
        viewModel.loadPost(postId)
    }

    val post = state.post
    LaunchedEffect(post) {
        if (post != null) {
            title = post.title
            body = post.body
        }
    }

    LaunchedEffect(state.savedPost) {
        state.savedPost?.let(onSaved)
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(onBack) {
            Text("Kembali")
        }
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text(state.error!!)
            post != null -> {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul") },
                )
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label =  { Text("Isi") }
                )
                Button(
                    onClick = { viewModel.savePost(postId, title, body) },
                    enabled = !state.isSaving && title.isNotBlank() && body.isNotBlank(),
                ) {
                    Text(if (state.isSaving) "Menyimpan..." else "Simpan")
                }
                state.saveError?.let { Text(it) }
            }
        }
    }

}
