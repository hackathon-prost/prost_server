package org.prost.core.domain.error

fun errorCodeFrom(error: Throwable) = when (error) {
    is UserAlreadyExistsException -> "USER_101"
    else -> "UNX_000"
}