package entity

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Polymorphic
sealed class VehicleEntity {
    abstract val vin: String
    abstract val make: String
    abstract val model: String
    abstract val year: Int
    abstract val color: String
    abstract val mileage: Int

    @Serializable
    @SerialName("Motorcycle")
    data class MotorcycleEntity(
        override val vin: String,
        override val make: String,
        override val model: String,
        override val year: Int,
        override val color: String,
        override val mileage: Int,
        val motoType: MotoType,
    ) : VehicleEntity()

    @Serializable
    @SerialName("Car")
    data class CarEntity(
        override val vin: String,
        override val make: String,
        override val model: String,
        override val year: Int,
        override val color: String,
        override val mileage: Int,
        val carType: models.CarType,
    ) : VehicleEntity()

    @Serializable
    @SerialName("CommercialVehicle")
    data class CommercialVehicleEntity(
        override val vin: String,
        override val make: String,
        override val model: String,
        override val year: Int,
        override val color: String,
        override val mileage: Int,
        val loadCapacity: Double,
    ) : VehicleEntity()
}


enum class CarType() {
    Sedan, Hatchback, Wagon
}

enum class MotoType() {
    Cross, Sport, GranTurismo
}