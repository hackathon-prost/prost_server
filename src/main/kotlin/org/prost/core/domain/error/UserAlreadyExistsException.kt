package org.prost.core.domain.error

class UserAlreadyExistsException(email: String): RuntimeException("User $email already exists")