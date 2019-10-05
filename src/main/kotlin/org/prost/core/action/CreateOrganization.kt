package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Categories
import org.prost.core.domain.Organization
import org.prost.core.domain.Organizations
import org.prost.core.domain.error.OrganizationAlreadyExistsException
import org.prost.core.infrastructure.Encryption
import org.prost.core.infrastructure.IdGenerator
import org.prost.http.dto.OrganizationDTO

class CreateOrganization(private val organizations: Organizations,
                         private val categories: Categories,
                         private val idGenerator: IdGenerator,
                         private val encryption: Encryption) {

    operator fun invoke(organizationDTO: OrganizationDTO): Single<Organization> {
        return organizations.findByEmail(organizationDTO.email)
            .isEmpty
            .flatMap { notExists ->
                if (!notExists) Single.error(OrganizationAlreadyExistsException(organizationDTO.email))
                else createOrganization(idGenerator.generate(), organizationDTO)
            }
    }

    private fun createOrganization(id: String, organizationDTO: OrganizationDTO): Single<Organization> {
        return categories.findBy(organizationDTO.category)
            .flatMap {category ->
                val organization = Organization(id, organizationDTO.name, organizationDTO.description,
                    category, organizationDTO.email, encryption.encrypt(organizationDTO.password),
                    organizationDTO.profileImage)

                organizations.add(organization).andThen(Single.just(organization))
            }
    }
}