package org.prost.core.action

import io.reactivex.Single
import org.prost.core.domain.Organization
import org.prost.core.domain.Organizations
import org.prost.core.infrastructure.Encryption

class LoginOrganization (private val organizations: Organizations,
                         private val encryption: Encryption) {

    operator fun invoke(email: String, password: String): Single<Organization> {
        return organizations.findBy(email, encryption.encrypt(password))
    }
}