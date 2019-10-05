package org.prost.core.domain.error

class UserNotFoundException(email: String): RuntimeException("User $email not found")