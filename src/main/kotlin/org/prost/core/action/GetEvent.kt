package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events

class GetEvent(private val events: Events) {
    operator fun invoke(id: String) : Single<Event> {
        return events.find(id)
    }
}