package com.gotechdynamics.koin.core.di

import com.gotechdynamics.koin.core.network.createApiClient
import org.koin.dsl.module

val appModule = module {
    single { createApiClient() }
}
