package org.prost.core.domain

data class Event (val id: String,
                  val organizationId: String,
                  val name: String,
                  val description: String,
                  val createdAt: Long,
                  val startDate: Long,
                  val image: String)