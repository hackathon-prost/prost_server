package org.prost.http.provider

import org.prost.core.infrastructure.repository.*


object Repositories {

    val users by lazy { InMemoryUsers() }
    val categories by lazy { InMemoryCategories() }
    val organizations by lazy { InMemoryOrganizations() }
    val events by lazy { InMemoryEvents() }
    val userEvents by lazy { InMemoryUserEvents() }
}