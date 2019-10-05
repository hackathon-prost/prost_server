package org.prost.core.domain

data class UserEventResponse(val userEvent: UserEvent,
                             val event: Event)