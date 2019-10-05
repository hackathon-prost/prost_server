package org.prost.config

object Environment {
    val PORT = "PORT".getOrDefaultTo("9090").toInt()
}

private fun String.getOrDefaultTo(defaultValue: String) = System.getenv(this) ?: defaultValue