package org.prost.http.dto

data class UserDTO(val email: String,
                   val password: String,
                   val name: String,
                   val lastName: String,
                   val phone: String,
                   val description: String,
                   val birthday: Long,
                   val interests: List<String>)