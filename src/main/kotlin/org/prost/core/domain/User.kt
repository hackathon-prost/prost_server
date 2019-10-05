package org.prost.core.domain

data class User(val id: String,
                val email: String,
                val password: String,
                val name: String,
                val lastName: String,
                val phone: String,
                val description: String,
                val birthday: Long,
                val interests: List<String>)