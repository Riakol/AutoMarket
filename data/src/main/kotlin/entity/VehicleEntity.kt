package entity

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Polymorphic
abstract class VehicleEntity {
    abstract  val vin : String
    abstract val brand: String
    abstract val model: String
    abstract val year: Int
    abstract val color: String
    abstract val mileage: Int
}

enum class MotoType() {
    Cross, Sport, GranTurismo
}

@Serializable
@SerialName("Motorcycle")
class MotorcycleEntity(
    override val vin: String,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val color: String,
    override val mileage: Int,
    val motoType: MotoType,
): VehicleEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MotorcycleEntity) return false
        return this.vin == other.vin && this.mileage == other.mileage
    }

    override fun hashCode(): Int {
        return Objects.hash(vin, mileage);
    }

    override fun toString(): String {
        return "Motorcycle(vin = $vin, brand = $brand, model = $model, year = $year, color = $color, mileage = $mileage)"
    }
}


enum class CarType() {
    Sedan, Hatchback, Wagon
}

@Serializable
@SerialName("Car")
class CarEntity(
    override val vin: String,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val color: String,
    override val mileage: Int,
    val carType: CarType,
): VehicleEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CarEntity) return false
        return this.vin == other.vin && this.mileage == other.mileage
    }

    override fun hashCode(): Int {
        return Objects.hash(vin, mileage);
    }

    override fun toString(): String {
        return "Car(vin = $vin, brand = $brand, model = $model, year = $year, color = $color, mileage = $mileage)"
    }
}

@Serializable
@SerialName("CommercialVehicle")
class CommercialVehicleEntity(
    override val vin: String,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val color: String,
    override val mileage: Int,
    val capacity: Int,
): VehicleEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CommercialVehicleEntity) return false
        return this.vin == other.vin && this.mileage == other.mileage
    }

    override fun hashCode(): Int {
        return Objects.hash(vin, mileage);
    }

    override fun toString(): String {
        return "CommercialVehicle(vin = $vin, brand = $brand)"
    }
}