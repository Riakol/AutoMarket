package org.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
@SerialName("Ad")
data class Ad(
    val id: Int,
    val ownerId: Int,
    val vin: String,
    val price: Double,
    val date: String,
    val isActive: Boolean = true,
    val priceHistory: List<Double>? = null,
    val reasonForCancellation: String? = null,
)