package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events
import org.prost.core.domain.error.EventNotFoundException

class InMemoryEvents : Events {

    private val events = mutableMapOf<String, Event>()

    override fun add(event: Event): Completable {
        return Completable.fromAction {
            events[event.id] = event
        }
    }

    override fun remove(id: String): Completable {
        return Completable.fromAction {
            events.remove(id)
        }
    }

    override fun find(id: String): Single<Event> {
        return Single.create { emitter ->
            val event = events[id]

            if (event != null) emitter.onSuccess(event)
            else emitter.onError(EventNotFoundException(id))
        }
    }

    override fun findBy(organizationId: String): Single<List<Event>> {
        return Single.create { emitter ->
            val filteredEvents = events.values.filter { it.organizationId == organizationId }
            emitter.onSuccess(filteredEvents)
        }
    }

    override fun findByName(name: String): Single<List<Event>> {
        return Single.create { emitter ->
            val filteredEvents = events.values.filter { it.name.toLowerCase().contains(name.toLowerCase())}
            emitter.onSuccess(filteredEvents)
        }
    }

    override fun getAll(): Single<List<Event>> {
        return Single.just(events.values.toList())
    }

}
