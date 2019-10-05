package org.prost.http.handler

import io.vertx.reactivex.ext.web.Router

interface Handler {
    fun register(router: Router)
}