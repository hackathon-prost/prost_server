package org.prost.http.mapper

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import org.prost.core.domain.Event
import org.prost.core.domain.Organization
import org.prost.core.domain.User

private const val ID = "id"
private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val NAME = "name"
private const val LAST_NAME = "lastName"
private const val PHONE = "phone"
private const val DESCRIPTION = "description"
private const val INTERESTS = "interests"
private const val BIRTHDAY = "birthday"
private const val PROFILE_IMAGE = "profileImage"
private const val CREATED_AT = "createdAt"
private const val START_DATE = "startDate"
private const val IMAGE = "image"


fun User.toJson(): JsonObject {
    return JsonObject()
        .put(ID, id)
        .put(EMAIL, email)
        .put(NAME, name)
        .put(LAST_NAME, lastName)
        .put(PHONE, phone)
        .put(DESCRIPTION, description)
        .put(BIRTHDAY, birthday)
        .put(INTERESTS, JsonArray(interests))
}

fun Organization.toJson(): JsonObject {
    return JsonObject()
        .put(ID, id)
        .put(NAME, name)
        .put(DESCRIPTION, description)
        .put(EMAIL, email)
        .put(PROFILE_IMAGE, profileImage)
}

fun Event.toJson(): JsonObject {
    return JsonObject()
        .put(ID, id)
        .put(NAME, name)
        .put(DESCRIPTION, description)
        .put(CREATED_AT, createdAt)
        .put(START_DATE, startDate)
        .put(IMAGE, image)
}