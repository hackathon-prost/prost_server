package org.prost.core.infrastructure.service

import org.joda.time.DateTime
import org.prost.core.infrastructure.Clock

class DefaultClock : Clock {
    override fun now(): DateTime {
        return DateTime.now()
    }

    override fun from(millis: Long): DateTime {
        return DateTime(millis)
    }

}