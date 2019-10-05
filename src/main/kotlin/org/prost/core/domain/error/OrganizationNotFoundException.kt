package org.prost.core.domain.error

class OrganizationNotFoundException(id: String): RuntimeException("Organization $id not found")