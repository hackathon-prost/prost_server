package org.prost.core.infrastructure.service

import org.prost.core.infrastructure.IdGenerator
import java.util.*

class DefaultIdGenerator : IdGenerator {
    override fun generate(): String = UUID.randomUUID().toString()
}