package org.prost.http.handler

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import org.prost.core.action.Greet
import org.prost.http.error.statusCodeFrom
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(GreetHandler::class.java)

private const val PATH = "/kotlin-template/greet/:lang"

class GreetHandler(private val greet: Greet) : Handler {
    override fun register(router: Router) {
        router.get(PATH).handler(this::handle)
    }


    private fun handle(context: RoutingContext) {
        val lang = context.pathParam("lang")

        greet(lang)
            .subscribe({ onSuccess(context, it) }, { onError(context, it) })
    }


    private fun onSuccess(context: RoutingContext, greet: String) {
        val greetingResponse = JsonObject().put("greeting", greet).encodePrettily()

        logger.info(greet)

        context.response().setStatusCode(200).end(greetingResponse)
    }

    private fun onError(context: RoutingContext, error: Throwable) {
        val statusCode = statusCodeFrom(error)
        val errorResponse = JsonObject().put("error", error.localizedMessage).encodePrettily()

        logger.error(error.localizedMessage)

        context.response().setStatusCode(statusCode).end(errorResponse)
    }
}