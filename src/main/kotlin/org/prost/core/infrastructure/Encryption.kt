package org.prost.core.infrastructure

interface Encryption {
    fun encrypt(data: String): String
}