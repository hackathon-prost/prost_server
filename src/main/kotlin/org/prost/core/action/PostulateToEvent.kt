package org.prost.core.action

import io.reactivex.Completable
import org.prost.core.domain.Events
import org.prost.core.domain.UserEvent
import org.prost.core.domain.UserEvents
import org.prost.core.domain.error.UserAlreadyPostulateException

class PostulateToEvent(private val events: Events,
                       private val userEvents: UserEvents) {

    operator fun invoke(eventId: String, userId: String): Completable {
        return events.find(eventId)
            .flatMapMaybe { userEvents.findBy(eventId, userId) }
            .isEmpty
            .flatMapCompletable { notExists ->
                if (!notExists) Completable.error(UserAlreadyPostulateException())
                else userEvents.add(UserEvent(eventId, userId))
            }
    }
}