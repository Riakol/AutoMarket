package models

import java.util.*


class Ad (
    val id: Int,
    val ownerId: Int,
    val vin: String,
    val price: Int,
    val date: String,
    val isActive: Boolean = true,
    val priceHistory: List<Int>? = null,
    val reasonForCancellation: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ad) return false
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id);
    }

    override fun toString(): String {
        return "Ad(id = $id, ownerId = $ownerId, vin = $vin, price = $price, date = $date, isActive = $isActive, priceHistory = $priceHistory)"
    }

    fun copy(
        id: Int = this.id,
        ownerId: Int = this.ownerId,
        vin: String = this.vin,
        price: Int = this.price,
        date: String = this.date,
        isActive: Boolean = this.isActive,
        priceHistory: List<Int>? = this.priceHistory?.toList(),
        reasonForCancellation: String? = this.reasonForCancellation
    ): Ad {
        return Ad(
            id = id,
            ownerId = ownerId,
            vin = vin,
            price = price,
            date = date,
            isActive = isActive,
            priceHistory = priceHistory,
            reasonForCancellation = reasonForCancellation
        )
    }
}