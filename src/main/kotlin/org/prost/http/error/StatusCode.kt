package org.prost.http.error

import org.prost.core.domain.error.GreetingsNotFoundException


fun statusCodeFrom(error: Throwable) = when (error) {
    is GreetingsNotFoundException -> 404
    else -> 500
}