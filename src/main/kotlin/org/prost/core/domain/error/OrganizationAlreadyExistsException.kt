package org.prost.core.domain.error

class OrganizationAlreadyExistsException(email: String) : RuntimeException("Organization with $email already exists")