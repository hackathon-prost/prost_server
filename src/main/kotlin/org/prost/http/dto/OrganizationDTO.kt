package org.prost.http.dto

data class OrganizationDTO(val name: String,
                           val description: String,
                           val category: String,
                           val email: String,
                           val password: String,
                           val profileImage: String)