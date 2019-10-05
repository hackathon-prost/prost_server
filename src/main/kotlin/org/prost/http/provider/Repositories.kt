package org.prost.http.provider

import org.prost.core.infrastructure.repository.InMemoryGreetings


object Repositories {
    val greetings by lazy { InMemoryGreetings() }
}