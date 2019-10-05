package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events

class GetEvents (private val events: Events) {
    operator fun invoke(): Single<List<Event>> {
        return events.getAll()
    }
}