package com.memory.localdatastorage

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform