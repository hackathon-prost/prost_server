package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.prost.core.domain.UserEvent
import org.prost.core.domain.UserEvents

class InMemoryUserEvents : UserEvents {

    private val userEvents = mutableSetOf<UserEvent>()

    override fun findBy(eventId: String, userId: String): Maybe<UserEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(userEvent: UserEvent): Completable {
        return Completable.fromAction {
            userEvents.add(userEvent)
        }
    }

    override fun remove(eventId: String, userId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findBy(userId: String): Single<List<UserEvent>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}