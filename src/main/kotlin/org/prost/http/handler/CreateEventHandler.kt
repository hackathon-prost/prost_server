package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.CreateEvent
import org.prost.core.domain.Event
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.dto.EventDTO
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/organization/:organizationId/event"

private const val ORGANIZATION_ID = "organizationId"
private const val NAME = "name"
private const val DESCRIPTION = "description"
private const val START_DATE = "startDate"
private const val IMAGE = "image"

class CreateEventHandler(private val createEvent: CreateEvent) : Handler {
    override fun register(router: Router) {
        router.post(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()
        val body = bodyAsJson

        val organizationId = pathParam(ORGANIZATION_ID)

        val name = body.name
        val description = body.description
        val startDate = body.startDate
        val image = body.image

        val eventDTO = EventDTO(name, description, startDate, image)

        createEvent(organizationId, eventDTO)
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(event: Event) {
        jsonContentType()
        statusCode = 201

        end(event.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }

    private val JsonObject.name get() = getString(NAME)
    private val JsonObject.description get() = getString(DESCRIPTION)
    private val JsonObject.startDate get() = getLong(START_DATE)
    private val JsonObject.image get() = getString(IMAGE)
}