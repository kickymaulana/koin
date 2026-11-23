package com.gotechdynamics.koin.feature.todos.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun TodosScreen(
    onTodoClick: (Long) -> Unit,
    onCreateClick: () -> Unit,
    onBack: () -> Unit,
    viewModel: TodosViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val error = state.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(onClick = onBack) {
            Text("Kembali")
        }

        Text(
            text = "Daftar Todo",
            style = MaterialTheme.typography.headlineMedium,
        )

        Button(onClick = onCreateClick) {
            Text("Tambah TODO")
        }

        if (state.isLoading){
            CircularProgressIndicator()
        }

        if (error != null) {
            Text(error)
            Button(onClick = { viewModel.loadTodos() }) {
                Text("Coba lagi")
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.todos, key = { it.id }) { todo ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTodoClick(todo.id) }
                        .padding(8.dp),
                ) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        if (todo.completed ) "Selesai" else "Belum selesai",
                    )
                }
            }
        }
    }
}

