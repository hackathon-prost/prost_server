package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.prost.core.domain.UserEvents

class InMemoryUserEvents : UserEvents {
    override fun add(userEvent: UserEvents): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(eventId: String, userId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findBy(userId: String): Single<List<UserEvents>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}