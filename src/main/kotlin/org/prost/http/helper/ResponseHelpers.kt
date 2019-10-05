package org.prost.http.helper

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.http.HttpServerResponse

fun messageError(code: String, message: String): String = JsonObject()
                                                    .put("error", JsonObject()
                                                        .put("code", code)
                                                        .put("message", message)
                                                    ).encodePrettily()

fun HttpServerResponse.jsonContentType() {
    putHeader("Content-Type", "application/json")
}