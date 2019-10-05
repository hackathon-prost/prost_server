package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.prost.core.domain.Event
import org.prost.core.domain.Events
import org.prost.core.domain.error.EventNotFoundException

class InMemoryEvents : Events {

    private val events = mutableMapOf<String, Event>(
        Pair("anEventId", Event("anEventId", "anOrganizationId", "Help the Earth", "Right we are experimenting a really dangerous climate change, we need you to stop this once for all", 1570308421049, 1571172461239, "https://images.newscientist.com/wp-content/uploads/2019/06/14160432/screenshot-2019-06-14-16.01.48.jpg")),
        Pair("anEventId2", Event("anEventId", "anOrganizationId", "Be careful with the air", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 1570308421049, 1571172461239, "https://images.newscientist.com/wp-content/uploads/2019/06/14160432/screenshot-2019-06-14-16.01.48.jpg")),
        Pair("anEventId3", Event("anEventId", "anOrganizationId1", "Save the animals", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 1570308421049, 1571172461239, "https://images.newscientist.com/wp-content/uploads/2019/06/14160432/screenshot-2019-06-14-16.01.48.jpg")),
        Pair("anEventId4", Event("anEventId", "anOrganizationId1", "Adopt a dog", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", 1570308421049, 1571172461239, "https://images.newscientist.com/wp-content/uploads/2019/06/14160432/screenshot-2019-06-14-16.01.48.jpg"))
    )

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
