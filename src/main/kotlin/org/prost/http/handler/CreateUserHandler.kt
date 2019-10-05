package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.CreateUser
import org.prost.core.domain.User
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.dto.UserDTO
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/user"

private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val NAME = "name"
private const val LAST_NAME = "lastName"
private const val PHONE = "phone"
private const val DESCRIPTION = "description"
private const val INTERESTS = "interests"
private const val BIRTHDAY = "birthday"

class CreateUserHandler(private val createUser: CreateUser) : Handler {

    override fun register(router: Router) {
        router.post(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()
        val body = bodyAsJson

        val userDTO = UserDTO(body.email,
            body.password, body.name, body.lastName, body.phone,
            body.description, body.birthday, body.interests)

        createUser(userDTO)
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(user: User) {
        statusCode = 201
        jsonContentType()

        end(user.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        statusCode = statusCodeFrom(error)
        jsonContentType()
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }

    private val JsonObject.email get() = getString(EMAIL)
    private val JsonObject.password get() = getString(PASSWORD)
    private val JsonObject.name get() = getString(NAME)
    private val JsonObject.lastName get() = getString(LAST_NAME)
    private val JsonObject.phone get() = getString(PHONE) ?: ""
    private val JsonObject.description get() = getString(DESCRIPTION) ?: ""
    private val JsonObject.birthday get() = getLong(BIRTHDAY)
    private val JsonObject.interests get() = getJsonArray(INTERESTS)?.map { e -> e.toString() }?.toList() ?: listOf()
}