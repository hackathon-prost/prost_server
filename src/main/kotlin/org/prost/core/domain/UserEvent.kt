package org.prost.core.domain

data class UserEvent(val eventId: String,
                     val userId: String,
                     val approved: Boolean? = false)