package models

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