package org.prost.core.domain

import io.reactivex.Completable
import io.reactivex.Single

interface UserEvents {
    fun add(userEvent: UserEvents): Completable
    fun remove(eventId: String, userId: String): Completable
    fun findBy(userId: String): Single<List<UserEvents>>
}