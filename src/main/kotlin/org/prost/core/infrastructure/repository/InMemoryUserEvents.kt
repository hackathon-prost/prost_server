package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.prost.core.domain.UserEvent
import org.prost.core.domain.UserEvents

class InMemoryUserEvents : UserEvents {

    private val userEvents = mutableSetOf<UserEvent>()

    override fun findBy(eventId: String, userId: String): Maybe<UserEvent> {
        return Maybe.create { emitter ->
            val userEvent = userEvents.find { it.eventId == eventId && it.userId == userId }
            if (userEvent != null) emitter.onSuccess(userEvent)
            emitter.onComplete()
        }
    }

    override fun add(userEvent: UserEvent): Completable {
        return Completable.fromAction {
            userEvents.add(userEvent)
        }
    }

    override fun remove(eventId: String, userId: String): Completable {
        return Completable.fromAction {
            userEvents.removeIf { eventId == it.eventId && userId == it.userId }
        }
    }

    override fun findBy(userId: String): Single<List<UserEvent>> {
        return Single.fromCallable {
            userEvents.filter { it.userId == userId }
        }
    }

}