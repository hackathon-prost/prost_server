package org.prost.http.handler

import org.prost.http.provider.VertxProvider.metrics
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext

private const val PATH = "/metrics"

class MetricsHandler : Handler {
    override fun register(router: Router) {
        router.get(PATH).handler(this::handle)
    }

    private fun handle(context: RoutingContext) {
        context.response().end(metrics.scrape())
    }
}