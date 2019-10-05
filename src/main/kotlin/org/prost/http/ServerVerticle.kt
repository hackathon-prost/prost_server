package org.prost.http

import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerOptions
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.http.HttpServer
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.handler.BodyHandler
import io.vertx.reactivex.ext.web.handler.CorsHandler
import org.prost.config.Banner
import org.prost.config.Environment
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(ServerVerticle::class.java)

class ServerVerticle : AbstractVerticle() {
    override fun start() {
        val router = createRouter()

        startHttpServer(router)
    }

    private fun createRouter(): Router {
        return Router.router(vertx)
            .apply(this::addCors)
            .apply(this::addBodyHandler)
            .apply(Routes::register)
    }

    private fun addCors(router: Router) {
        router.route().handler(CorsHandler.create("*")
            .allowedMethod(HttpMethod.GET)
            .allowedMethod(HttpMethod.POST)
            .allowedMethod(HttpMethod.PUT)
            .allowedMethod(HttpMethod.DELETE)
            .allowedHeader("Content-Type"));
    }

    private fun startHttpServer(router: Router) {
        vertx.createHttpServer(HttpServerOptions().setHandle100ContinueAutomatically(true))
            .requestHandler(router)
            .rxListen(Environment.PORT)
            .subscribe(this::onServerStarted, this::onServerStartError)
    }

    private fun addBodyHandler(router: Router) {
        router.route().handler(BodyHandler.create())
    }

    private fun onServerStarted(httpServer: HttpServer) {
        Banner.print()
        println("\nServer listening on :${httpServer.actualPort()}")
    }

    private fun onServerStartError(error: Throwable) {
        logger.info("Server not started")
        logger.error(error.message, error)
    }
}