package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.LoginOrganization
import org.prost.core.domain.Organization
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/organization/login"

private const val EMAIL = "email"
private const val PASSWORD = "password"

class LoginOrganizationHandler(private val loginOrganization: LoginOrganization) : Handler {
    override fun register(router: Router) {
        router.post(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()
        val body = bodyAsJson

        val email = body.email
        val password = body.password

        loginOrganization(email, password)
            .subscribe({ response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(organization: Organization) {
        jsonContentType()
        statusCode = 200

        end(organization.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }

    private val JsonObject.email get() = getString(EMAIL)
    private val JsonObject.password get() = getString(PASSWORD)
}