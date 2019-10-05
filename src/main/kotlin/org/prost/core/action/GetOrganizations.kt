package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Organization
import org.prost.core.domain.Organizations

class GetOrganizations (private val organizations: Organizations) {
    operator fun invoke(): Single<List<Organization>> {
        return organizations.getAll()
    }
}