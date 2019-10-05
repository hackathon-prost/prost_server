package org.prost.core.domain

data class Organization (val id: String,
                         val name: String,
                         val description: String,
                         val category: Category,
                         val email: String,
                         val password: String,
                         val profileImage: String)