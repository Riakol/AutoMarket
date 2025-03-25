package org.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Database(
    val vehicles: List<Vehicle> = emptyList(),
    val owners: List<Owner> = emptyList(),
    val ads: List<Ad> = emptyList(),
)