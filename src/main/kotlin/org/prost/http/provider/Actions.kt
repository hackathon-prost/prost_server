package org.prost.http.provider

import org.prost.core.action.*

object Actions {

    val createUser by lazy { CreateUser(Repositories.users, Services.idGenerator, Services.encryption) }

    val loginUser by lazy { LoginUser(Repositories.users, Services.encryption) }

    val createOrganization by lazy { CreateOrganization(Repositories.organizations, Repositories.categories,
        Services.idGenerator, Services.encryption) }

    val loginOrganization by lazy { LoginOrganization(Repositories.organizations, Services.encryption) }

    val createEvent by lazy { CreateEvent(Repositories.events, Repositories.organizations,
        Services.idGenerator, Services.clock) }

    val getOrganizations by lazy { GetOrganizations(Repositories.organizations) }

    val getOrganizationsByName by lazy { GetOrganizationsByName(Repositories.organizations) }

    val getEvents by lazy { GetEvents(Repositories.events) }

    val getEvent by lazy { GetEvent(Repositories.events) }

    val getEventsOrganization by lazy { GetEventsOrganization(Repositories.events, Repositories.organizations) }

    val getEventsByName by lazy { GetEventsByName(Repositories.events) }
}