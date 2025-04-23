package entity

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class OwnerEntity (
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AdEntity) return false
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id);
    }

    override fun toString(): String {
        return "Ad(id = $id, name = $name, phone = $phone, email = $email)"
    }
}