package com.gotechdynamics.koin.core.di

import org.koin.core.context.startKoin
import com.gotechdynamics.koin.feature.todos.di.todosModule
import com.gotechdynamics.koin.feature.posts.di.postsModule

fun initKoin() {
    startKoin {
        modules(appModule, postsModule, todosModule)
    }
}