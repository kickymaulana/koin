package com.gotechdynamics.koin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.gotechdynamics.koin.feature.posts.presentation.CreatePostScreen
import com.gotechdynamics.koin.feature.posts.presentation.EditPostScreen
import com.gotechdynamics.koin.feature.posts.presentation.PostsScreen
import com.gotechdynamics.koin.feature.todos.presentation.TodosScreen
import com.gotechdynamics.koin.navigation.CreatePostRoute
import com.gotechdynamics.koin.navigation.EditPostRoute
import com.gotechdynamics.koin.navigation.PostsRoute
import com.gotechdynamics.koin.navigation.TodosRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val navigationConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(PostsRoute::class, PostsRoute.serializer())
            subclass(EditPostRoute::class, EditPostRoute.serializer())
            subclass(CreatePostRoute::class, CreatePostRoute.serializer())
            subclass(TodosRoute::class, TodosRoute.serializer())
        }
    }
}

@Composable
fun App() {
    MaterialTheme {
        val backStack = rememberNavBackStack(navigationConfig, PostsRoute)

        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) backStack.removeLastOrNull()
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<PostsRoute> {
                    PostsScreen(
                        onPostClick = { id ->
                            backStack.add(EditPostRoute(id))
                        },
                        onCreateClick = {
                            backStack.add(CreatePostRoute)
                        },
                        onTodosClick = {
                            backStack.add(TodosRoute)
                        }
                    )
                }

                entry<EditPostRoute> { route ->
                    EditPostScreen(
                        postId = route.postId,
                        onBack = { backStack.removeLastOrNull() },
                        onSaved = { updatedPost ->
                            backStack.removeLastOrNull()
                        }
                    )
                }
                entry<CreatePostRoute> {
                    CreatePostScreen(
                        onBack = { backStack.removeLastOrNull() },
                        onCreated = { post ->
                            backStack.removeLastOrNull()
                        },
                    )
                }
                entry<TodosRoute> {
                    TodosScreen(
                        onTodoClick = { id ->
                        },
                        onCreateClick = {
                        },
                        onBack = {
                            backStack.removeLastOrNull()
                        },

                        )
                }
            }
        )

    }
}
