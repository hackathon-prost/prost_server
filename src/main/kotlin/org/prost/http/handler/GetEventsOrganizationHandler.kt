package org.prost.http.handler

import io.vertx.core.json.JsonArray
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.GetEventsOrganization
import org.prost.core.domain.Event
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/events/organization/:organizationId"

private const val ORGANIZATION_ID = "organizationId"

class GetEventsOrganizationHandler(private val getEventsOrganization: GetEventsOrganization) : Handler {
    override fun register(router: Router) {
        router.get(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()

        val organizationId = pathParam(ORGANIZATION_ID)

        getEventsOrganization(organizationId)
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(events: List<Event>) {
        jsonContentType()
        statusCode = 200

        end(
            JsonArray(
                events.map { it.toJson() }
            ).encodePrettily()
        )
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }

}