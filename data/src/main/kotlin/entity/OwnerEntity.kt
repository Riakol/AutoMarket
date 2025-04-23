package entity

import kotlinx.serialization.Serializable

@Serializable
data class OwnerEntity (
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
)
