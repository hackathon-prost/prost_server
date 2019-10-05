package org.prost.http.handler

import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.GetUser
import org.prost.core.domain.User
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/users/:userId"

class GetUserHandler(private val getUser: GetUser) : Handler {
    override fun register(router: Router) {
        router.get(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()

        val userId = pathParam("userId")

        getUser(userId)
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(user: User) {
        jsonContentType()
        statusCode = 200

        end(user.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }

}