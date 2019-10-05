package org.prost.http.handler

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext

private const val PATH = "/info"

class InfoHandler : Handler {
    override fun register(router: Router) {
        router.get(PATH)
            .handler(this::handle)
    }

    private fun handle(context: RoutingContext) {
        val versions = JsonArray()
            .add(JsonObject()
                .put("name", "gradle" )
                .put("version", "5.4.1")
                .put("url", "https://github.com/gradle/gradle/releases/tag/v5.4.1"))
            .add(JsonObject()
                .put("name", "Kotlin" )
                .put("version", "1.3.31")
                .put("url", "https://github.com/JetBrains/kotlin/releases/tag/v1.3.31"))
            .add(JsonObject()
                .put("name", "Spek" )
                .put("version", "2.0.2")
                .put("url", "https://github.com/spekframework/spek/releases/tag/2.0.2"))
            .add(JsonObject()
                .put("name", "Log4J" )
                .put("version", "2.11.2")
                .put("url", "https://github.com/apache/logging-log4j2/releases/tag/log4j-2.11.2"))
            .add(JsonObject()
                .put("name", "Vertx" )
                .put("version", "3.7.0")
                .put("url", "https://github.com/eclipse-vertx/vert.x/releases/tag/3.7.0"))
            .add(JsonObject()
                .put("name", "RxKotlin" )
                .put("version", "2.3.0")
                .put("url", "https://github.com/ReactiveX/RxKotlin/releases/tag/2.3.0"))
            .add(JsonObject()
                .put("name", "Micrometer Metrics Prometheus" )
                .put("version", "1.1.4")
                .put("url", "https://github.com/micrometer-metrics/micrometer/releases/tag/v1.1.4"))
            .encodePrettily()

        context.response().end(versions)
    }
}