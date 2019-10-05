package org.prost.http.handler

import io.vertx.core.json.JsonArray
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.GetEvent
import org.prost.core.domain.Event
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson


private const val PATH = "/prost/api/events/:id"
private const val ID = "id"

class GetEventHandler(private val getEvent: GetEvent) : Handler {
    override fun register(router: Router) {
        router.get(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()

        val id = pathParam(ID)

        getEvent(id)
            .subscribe({ response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(event: Event) {
        jsonContentType()
        statusCode = 200

        end(event.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }
}