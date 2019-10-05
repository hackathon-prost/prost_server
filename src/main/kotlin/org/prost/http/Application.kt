package org.prost.http

import io.reactivex.plugins.RxJavaPlugins
import io.vertx.reactivex.core.RxHelper
import org.prost.http.provider.VertxProvider.vertx
import org.slf4j.LoggerFactory
private val logger = LoggerFactory.getLogger(Application.javaClass.canonicalName)

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        configureRxJavaPlugins()

        vertx.deployVerticle(ServerVerticle::class.java.canonicalName)
    }

    private fun configureRxJavaPlugins() {
        RxHelper.blockingScheduler(vertx, false)
        RxJavaPlugins.setIoSchedulerHandler { RxHelper.blockingScheduler(vertx) }
        RxJavaPlugins.setNewThreadSchedulerHandler { RxHelper.scheduler(vertx) }
        RxJavaPlugins.setSingleSchedulerHandler { RxHelper.scheduler(vertx) }
        // Global Error Handler for RxJava
        RxJavaPlugins.setErrorHandler { logger.error(it.localizedMessage, it) }
    }
}