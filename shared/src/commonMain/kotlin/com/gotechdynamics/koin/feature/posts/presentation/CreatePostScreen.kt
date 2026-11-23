package com.gotechdynamics.koin.feature.posts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
fun CreatePostScreen(
    onBack: () -> Unit,
    onCreated: (Post) -> Unit,
    viewModel: CreatePostViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    LaunchedEffect(state.createdPost) {
        state.createdPost?.let(onCreated)
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Tambah post")

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isSaving,
        )

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Isi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isSaving,
            minLines = 4,
        )
        state.error?.let { Text(it) }

        Button(
            onClick = { viewModel.createPost(title, body) },
            enabled = !state.isSaving && title.isNotBlank() && body.isNotBlank(),
        ) {
            Text(if (state.isSaving) "Menyimpan..." else "Simpan")
        }

        Button(
            onClick = onBack,
            enabled = !state.isSaving,
        ) {
            Text("Batal")
        }
    }
}
