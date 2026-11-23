package com.gotechdynamics.koin.feature.todos.di

import com.gotechdynamics.koin.feature.todos.data.TodosApi
import com.gotechdynamics.koin.feature.todos.data.TodosRepository
import com.gotechdynamics.koin.feature.todos.presentation.TodosViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val todosModule = module {
    single { TodosApi(get()) }
    single { TodosRepository(get()) }
    viewModel { TodosViewModel(get()) }
}
