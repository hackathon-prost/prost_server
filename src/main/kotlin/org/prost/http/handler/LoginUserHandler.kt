package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.LoginUser
import org.prost.core.domain.User
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/user/login"

private const val EMAIL = "email"
private const val PASSWORD = "password"

class LoginUserHandler(private val loginUser: LoginUser) : Handler {
    override fun register(router: Router) {
        router.post(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()

        val body = bodyAsJson
        val email = body.email
        val password = body.password

        loginUser(email, password)
            .subscribe({ response.onSuccess(it) }, { response.onError(it) })
    }

    private fun HttpServerResponse.onSuccess(user: User) {
        statusCode = 200
        jsonContentType()

        end(user.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        jsonContentType()

        end(messageError(errorCode, error.localizedMessage))
    }

    private val JsonObject.email get() = getString(EMAIL)
    private val JsonObject.password get() = getString(PASSWORD)
}