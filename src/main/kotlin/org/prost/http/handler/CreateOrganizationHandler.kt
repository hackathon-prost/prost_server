package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.CreateOrganization
import org.prost.core.domain.Organization
import org.prost.core.domain.error.errorCodeFrom
import org.prost.http.dto.OrganizationDTO
import org.prost.http.error.statusCodeFrom
import org.prost.http.helper.jsonContentType
import org.prost.http.helper.messageError
import org.prost.http.mapper.toJson

private const val PATH = "/prost/api/organization"

private const val NAME = "name"
private const val DESCRIPTION = "description"
private const val CATEGORY = "category"
private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val PROFILE_IMAGE = "profileImage"

class CreateOrganizationHandler(private val createOrganization: CreateOrganization) : Handler {
    override fun register(router: Router) {
        router.post(PATH).handler { it.handle() }
    }

    private fun RoutingContext.handle() {
        val response = response()
        val body = bodyAsJson

        val organizationDTO = OrganizationDTO(
            body.name,
            body.description,
            body.category,
            body.email,
            body.password,
            body.profileImage
        )

        createOrganization(organizationDTO)
            .subscribe({response.onSuccess(it)}, {response.onError(it)})
    }

    private fun HttpServerResponse.onSuccess(organization: Organization) {
        jsonContentType()
        statusCode = 201

        end(organization.toJson().encodePrettily())
    }

    private fun HttpServerResponse.onError(error: Throwable) {
        jsonContentType()
        statusCode = statusCodeFrom(error)
        val errorCode = errorCodeFrom(error)

        end(messageError(errorCode, error.localizedMessage))
    }

    private val JsonObject.name get() = getString(NAME)
    private val JsonObject.description get() = getString(DESCRIPTION)
    private val JsonObject.category get() = getString(CATEGORY)
    private val JsonObject.email get() = getString(EMAIL)
    private val JsonObject.password get() = getString(PASSWORD)
    private val JsonObject.profileImage get() = getString(PROFILE_IMAGE)
}