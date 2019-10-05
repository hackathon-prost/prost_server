package org.prost.core.domain

import io.reactivex.Single

interface Greetings {
    fun find(lang: String) : Single<String>
}