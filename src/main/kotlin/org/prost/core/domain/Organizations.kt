package org.prost.core.domain

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Organizations {
    fun add(organization: Organization): Completable
    fun remove(id: String): Completable
    fun find(id: String): Single<Organization>
    fun findBy(name: String): Single<List<Organization>>
    fun findBy(email: String, password: String): Single<Organization>
    fun findByEmail(email: String): Maybe<Organization>
    fun getAll(): Single<List<Organization>>
}