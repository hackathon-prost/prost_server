package org.prost.core.domain

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface UserEvents {
    fun add(userEvent: UserEvent): Completable
    fun remove(eventId: String, userId: String): Completable
    fun findBy(eventId: String, userId: String): Maybe<UserEvent>
    fun findBy(userId: String): Single<List<UserEvent>>
}