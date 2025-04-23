package entity

import kotlinx.serialization.Serializable

@Serializable
data class AdEntity(
    val id: Int,
    val ownerId: Int,
    val vin: String,
    val price: Double,
    val date: String,
    val isActive: Boolean = true,
    val priceHistory: List<Double>? = null,
    val reasonForCancellation: String? = null,
)