package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events
import org.prost.core.domain.Organizations
import org.prost.core.infrastructure.Clock
import org.prost.core.infrastructure.IdGenerator
import org.prost.http.dto.EventDTO

class CreateEvent(private val events: Events,
                  private val organizations: Organizations,
                  private val idGenerator: IdGenerator,
                  private val clock: Clock) {

    operator fun invoke(organizationId: String, eventDTO: EventDTO): Single<Event> {
        return organizations.find(organizationId)
            .flatMap { organization ->
                val event = Event(idGenerator.generate(),
                    organization.id,
                    eventDTO.name,
                    eventDTO.description,
                    clock.now().millis,
                    eventDTO.startDate,
                    eventDTO.image)

                events.add(event).andThen(Single.just(event))
            }
    }
}