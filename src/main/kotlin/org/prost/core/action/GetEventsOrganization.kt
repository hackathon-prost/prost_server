package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events
import org.prost.core.domain.Organizations

class GetEventsOrganization (private val events: Events,
                             private val organizations: Organizations) {
    operator fun invoke(organizationId: String): Single<List<Event>> {
        return organizations.find(organizationId)
            .flatMap { events.findBy(organizationId) }
    }
}