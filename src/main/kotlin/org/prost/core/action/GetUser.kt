package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.User
import org.prost.core.domain.Users
import org.prost.core.domain.error.UserNotFoundException

class GetUser(private val users: Users) {
    operator fun invoke(userId: String): Single<User> {
        return users.findById(userId)
            .switchIfEmpty(Single.defer { Single.error<User>(UserNotFoundException(userId)) })
    }
}