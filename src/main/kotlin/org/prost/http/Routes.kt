package org.prost.http

import io.vertx.reactivex.ext.web.Router
import org.prost.http.handler.*
import org.prost.http.provider.Actions
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(Routes.javaClass.canonicalName)

object Routes {
    fun register(router: Router) {
        router.use(GreetHandler(Actions.greet))
        router.use(GreetPrivateHandler(Actions.greet))

        // Endpoints for health check, Prometheus metrics and versions info.
        router.use(HealthCheckHandler())
        router.use(MetricsHandler())
        router.use(InfoHandler())
        router.addFailureHandler()
    }

    private fun Router.addFailureHandler() {
        routes.forEach { r ->
            r.failureHandler { rc ->
                logger.error(rc.failure().localizedMessage, rc.failure())
            }
        }
    }

    private fun Router.use(handler: Handler) = handler.register(this)
}
