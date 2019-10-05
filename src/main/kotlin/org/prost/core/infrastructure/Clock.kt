package org.prost.core.infrastructure

import org.joda.time.DateTime

interface Clock {
    fun now(): DateTime
    fun from(millis: Long): DateTime
}