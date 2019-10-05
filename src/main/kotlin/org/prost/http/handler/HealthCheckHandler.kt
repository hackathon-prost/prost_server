package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext

private const val PATH = "/health"

class HealthCheckHandler : Handler {
    override fun register(router: Router) {
        router.get(PATH)
            .handler(this::handle)
    }

    private fun handle(context: RoutingContext) {
        val healthResponse = JsonObject().put("status", "healthy").encodePrettily()

        context.response().setStatusCode(200).end(healthResponse)
    }
}