package org.example.database

import kotlinx.serialization.Serializable
import org.example.models.Ad
import org.example.models.Owner
import org.example.models.Vehicle

@Serializable
data class Database(
    val vehicles: List<Vehicle> = emptyList(),
    val owners: List<Owner> = emptyList(),
    val ads: List<Ad> = emptyList(),
)