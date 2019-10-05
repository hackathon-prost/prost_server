package org.prost.core.infrastructure.repository

import io.reactivex.Single
import org.prost.core.domain.Greetings
import org.prost.core.domain.error.GreetingsNotFoundException

class InMemoryGreetings : Greetings {
    override fun find(lang: String): Single<String> =
        Single.just(lang)
            .filter { it == "en" }
            .switchIfEmpty(Single.error(GreetingsNotFoundException()))
            .map { "Hello, Platform" }
}