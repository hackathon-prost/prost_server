package org.prost.core.action

import io.reactivex.Single
import io.reactivex.rxkotlin.toObservable
import org.prost.core.domain.Events
import org.prost.core.domain.UserEventResponse
import org.prost.core.domain.UserEvents

class GetUserPostulations(private val userEvents: UserEvents,
                          private val events: Events) {

    operator fun invoke(userId: String): Single<List<UserEventResponse>> {
        return userEvents.findBy(userId)
            .flatMapObservable { it.toObservable() }
            .flatMapSingle { userEvent ->
                events.find(userEvent.eventId).map {
                    UserEventResponse(userEvent, it)
                }
            }.toList()
    }
}