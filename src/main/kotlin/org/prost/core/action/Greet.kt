package org.prost.core.action

import org.prost.core.domain.Greetings


class Greet(private val greetings: Greetings) {
    operator fun invoke(lang: String) = greetings.find(lang)
}