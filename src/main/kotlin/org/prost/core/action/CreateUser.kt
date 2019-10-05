package org.prost.core.action

import io.reactivex.Single
import org.prost.core.infrastructure.IdGenerator
import org.prost.core.domain.User
import org.prost.core.domain.Users
import org.prost.core.infrastructure.Encryption
import org.prost.core.domain.error.UserAlreadyExistsException
import org.prost.http.dto.UserDTO

class CreateUser(private val users: Users,
                 private val idGenerator: IdGenerator,
                 private val encryption: Encryption) {
    operator fun invoke(userDTO: UserDTO): Single<User> {
        return users.findByEmail(userDTO.email)
            .isEmpty
            .flatMap {notExists -> createAndGet(notExists, userDTO) }
    }

    private fun createAndGet(notExists: Boolean, userDTO: UserDTO): Single<User> {
        return if (!notExists)
            Single.error(UserAlreadyExistsException(userDTO.email))
        else
            createUserWith(idGenerator.generate(), userDTO)
    }


    private fun createUserWith(id: String, userDTO: UserDTO): Single<User> {
        val password = encryption.encrypt(userDTO.password)
        val user = User(id, userDTO.email, password, userDTO.name, userDTO.lastName,
            userDTO.phone, userDTO.description, userDTO.birthday, userDTO.interests)

        return Single.defer {
            users.add(user).andThen(Single.just(user))
        }
    }
}

// User: name, lastname, email, phone, categories(lists), password, description
// Categories: id, name
// UserEvent: idUser, idEvent, userStatus