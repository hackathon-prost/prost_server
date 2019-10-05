package org.prost.core.domain

import io.reactivex.Completable
import io.reactivex.Single

interface Categories {
    fun add(category: Category): Completable
    fun findAll(): Single<List<Category>>
    fun remove(name: String): Completable
    fun findBy(name: String): Single<Category>
}