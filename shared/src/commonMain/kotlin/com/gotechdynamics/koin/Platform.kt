package com.gotechdynamics.koin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform