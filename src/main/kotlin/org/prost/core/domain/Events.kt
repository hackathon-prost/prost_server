package org.prost.core.domain

import io.reactivex.Completable
import io.reactivex.Single

interface Events {
    fun add(event: Event): Completable
    fun remove(id: String): Completable
    fun find(id: String): Single<Event>
    fun findByName(name: String): Single<List<Event>>
    fun findBy(organizationId: String): Single<List<Event>>
    fun getAll(): Single<List<Event>>
}