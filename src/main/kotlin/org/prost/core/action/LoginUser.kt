package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.User
import org.prost.core.domain.Users
import org.prost.core.domain.error.UserNotFoundException
import org.prost.core.infrastructure.Encryption

class LoginUser(private val users: Users,
                private val encryption: Encryption) {

    operator fun invoke(email: String, password: String): Single<User> {
        return users.find(email, encryption.encrypt(password))
            .switchIfEmpty(Single.defer { Single.error<User>(UserNotFoundException(email)) })
    }
}