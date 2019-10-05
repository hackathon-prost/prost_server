package org.prost.http.provider

import io.micrometer.prometheus.PrometheusMeterRegistry
import io.vertx.core.VertxOptions
import io.vertx.micrometer.MicrometerMetricsOptions
import io.vertx.micrometer.VertxPrometheusOptions
import io.vertx.micrometer.backends.BackendRegistries
import io.vertx.reactivex.core.Vertx

object VertxProvider {
    val vertx: Vertx by lazy { Vertx.vertx(vertxOptions) }

    private val vertxOptions by lazy { VertxOptions().setMetricsOptions(metricsOptions) }

    private val prometheusOptions by lazy { VertxPrometheusOptions().setEnabled(true) }

    private val metricsOptions by lazy {
        MicrometerMetricsOptions()
            .setPrometheusOptions(prometheusOptions)
            .setEnabled(true)
    }

    val metrics by lazy {
        BackendRegistries.getDefaultNow().let { it as PrometheusMeterRegistry }
    }

}
