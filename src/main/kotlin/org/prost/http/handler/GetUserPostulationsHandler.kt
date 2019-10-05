package org.prost.http.handler

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.GetUserPostulations
import org.prost.core.domain.UserEventResponse
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/users/:userId/postulations"

class GetUserPostulationsHandler(private val getUserPostulations: GetUserPostulations) : Handler {
    override fun register(router: Router) {
        router.get(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()

        val userId = pathParam("userId")

        getUserPostulations(userId)
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(userEvents: List<UserEventResponse>) {
        jsonContentType()
        statusCode = 200

        end(
            JsonObject()
                .put("postulations", JsonArray(userEvents.map { it.toJson() }))
                .encodePrettily()
        )
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }
}