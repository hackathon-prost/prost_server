package org.prost.core.domain

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Users {
    fun add(user: User): Completable
    fun findById(id: String): Maybe<User>
    fun findByEmail(email: String): Maybe<User>
    fun find(username: String, password: String): Maybe<User>
    fun remove(id: String) : Completable
}