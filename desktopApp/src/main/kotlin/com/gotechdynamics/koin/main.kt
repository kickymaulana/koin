package com.gotechdynamics.koin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.gotechdynamics.koin.core.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KOIN",
        ) {
            App()
        }
    }
}