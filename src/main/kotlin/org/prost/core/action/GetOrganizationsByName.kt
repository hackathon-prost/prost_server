package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Organization
import org.prost.core.domain.Organizations

class GetOrganizationsByName(private val organizations: Organizations) {
    operator fun invoke(name: String): Single<List<Organization>> {
        return organizations.findBy(name)
    }
}