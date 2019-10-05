package org.prost.core.infrastructure

interface IdGenerator {
    fun generate(): String
}