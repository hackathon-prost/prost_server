package org.prost.http.handler

import io.vertx.core.json.JsonArray
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.GetOrganizations
import org.prost.core.domain.Organization
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/organizations"

class GetOrganizationsHandler (private val getOrganizations: GetOrganizations): Handler {
    override fun register(router: Router) {
        router.get(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()

        getOrganizations()
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(organizations: List<Organization>) {
        jsonContentType()
        statusCode = 200

        end(
            JsonArray(
                organizations.map { it.toJson() }
            ).encodePrettily()
        )
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        val errorCode = errorCodeFrom(error)
        statusCode = statusCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }
}