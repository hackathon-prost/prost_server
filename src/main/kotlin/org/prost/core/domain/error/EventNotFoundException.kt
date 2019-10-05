package org.prost.core.domain.error

class EventNotFoundException(id: String): RuntimeException("Event With id $id,  not found")