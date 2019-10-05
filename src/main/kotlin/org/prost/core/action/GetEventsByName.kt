package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events

class GetEventsByName(private val events: Events) {

    operator fun invoke(name: String): Single<List<Event>> {
        return events.findByName(name)
    }
}