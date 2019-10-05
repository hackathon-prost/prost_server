package org.prost.http.provider

import org.prost.core.action.Greet

object Actions {
    val greet by lazy { Greet(Repositories.greetings) }
}