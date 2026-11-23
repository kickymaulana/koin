package com.gotechdynamics.koin.feature.posts.di

import com.gotechdynamics.koin.feature.posts.data.PostsApi
import com.gotechdynamics.koin.feature.posts.data.PostsRepository
import com.gotechdynamics.koin.feature.posts.presentation.CreatePostViewModel
import com.gotechdynamics.koin.feature.posts.presentation.EditPostViewModel
import com.gotechdynamics.koin.feature.posts.presentation.PostsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val postsModule = module {
    single { PostsApi(get()) }
    single { PostsRepository(get()) }
    viewModel { PostsViewModel(get()) }
    viewModel { EditPostViewModel(get()) }
    viewModel { CreatePostViewModel(get()) }
}