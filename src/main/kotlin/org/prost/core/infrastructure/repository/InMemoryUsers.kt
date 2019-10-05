package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.prost.core.domain.User
import org.prost.core.domain.Users

class InMemoryUsers : Users {

    private val users = mutableMapOf<String, User>(
        Pair("carlos@test.com", User("anUserId", "carlos@test.com", "AIHcm9tS0E3CADbb2DE+0FU=", "Carlos", "Montes", "555-1234543", "Software Engineer", 723088800000, listOf())),
        Pair("fer@test.com", User("anUserId2", "fer@test.com", "AIHcm9tS0E3CADbb2DE+0FU=", "Fernando", "", "555-1234543", "FrontEnd Developer", 723088800000, listOf())),
        Pair("lau@test.com", User("anUserId3", "lau@test.com", "AIHcm9tS0E3CADbb2DE+0FU=", "Lautaro", "Lobo", "555-1234543", "FrontEnd Developer", 723088800000, listOf())),
        Pair("rodri@test.com", User("anUserId4", "rodri@test.com", "AIHcm9tS0E3CADbb2DE+0FU=", "Rodrigo", "", "555-1234543", "FrontEnd Developer", 723088800000, listOf()))
    )

    override fun add(user: User): Completable {
        return Completable.fromAction {
            users[user.email] = user
        }
    }

    override fun findById(id: String): Maybe<User> {
        return Maybe.create { emitter ->
            val user = users.values.find { it.id == id }
            if (user != null) {
                emitter.onSuccess(user)
            }

            emitter.onComplete()
        }
    }

    override fun findByEmail(email: String): Maybe<User> {
        return Maybe.create {emitter ->
            val user = users[email]

            if (user != null) {
                emitter.onSuccess(user)
            }

            emitter.onComplete()
        }
    }

    override fun find(username: String, password: String): Maybe<User> {
        return Maybe.create {emitter ->
            val user = users.values.find { it.email == username && it.password == password }

            if (user != null) {
                emitter.onSuccess(user)
            }

            emitter.onComplete()
        }
    }

    override fun remove(id: String): Completable {
        return Completable.fromAction {
            val user = users.values.find { it.id == id }
            if (user != null) users.remove(user.email)
        }
    }

}