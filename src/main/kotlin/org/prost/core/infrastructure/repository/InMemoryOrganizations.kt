package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.prost.core.domain.Organization
import org.prost.core.domain.Organizations
import org.prost.core.domain.error.OrganizationNotFoundException

class InMemoryOrganizations : Organizations {

    override fun getAll(): Single<List<Organization>> {
        return Single.just(organizations.values.toList())
    }

    override fun findBy(email: String, password: String): Single<Organization> {
        return Single.create { emitter ->
            val organization = organizations.values.find { it.email == email && it.password == password }

            if (organization != null) {
                emitter.onSuccess(organization)
            } else {
                emitter.onError(OrganizationNotFoundException(email))
            }
        }
    }

    override fun findByEmail(email: String): Maybe<Organization> {
        return Maybe.create { emitter ->
            val organization = organizations.values.find { it.email == email }

            if (organization != null) {
                emitter.onSuccess(organization)
            }

            emitter.onComplete()
        }
    }

    private val organizations = mutableMapOf<String, Organization>()

    override fun add(organization: Organization): Completable {
        return Completable.fromAction {
            organizations[organization.id] = organization
        }
    }

    override fun remove(id: String): Completable {
        return Completable.fromAction {
            organizations.remove(id)
        }
    }

    override fun find(id: String): Single<Organization> {
        return Single.create { emitter ->
            val organization = organizations[id]

            if (organization != null) {
                emitter.onSuccess(organization)
            } else {
                emitter.onError(OrganizationNotFoundException(id))
            }
        }
    }

    override fun findBy(name: String): Single<List<Organization>> {
        return Single.create { emitter ->
            val filterOrganizations = organizations.values.filter { it.name.toLowerCase().contains(name.toLowerCase()) }

            emitter.onSuccess(filterOrganizations)
        }

    }
}