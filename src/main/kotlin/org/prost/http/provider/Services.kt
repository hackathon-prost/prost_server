package org.prost.http.provider

import org.prost.core.infrastructure.service.DefaultClock
import org.prost.core.infrastructure.service.DefaultEncryption
import org.prost.core.infrastructure.service.DefaultIdGenerator

object Services {

    val idGenerator by lazy { DefaultIdGenerator() }
    val encryption by lazy { DefaultEncryption() }
    val clock by lazy { DefaultClock() }
}