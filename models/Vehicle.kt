package org.example.models

import kotlinx.serialization.*


@Serializable
@Polymorphic
sealed class Vehicle {
    abstract val vin: String
    abstract val make: String
    abstract val model: String
    abstract val year: Int
    abstract val color: String
    abstract val mileage: Int

    @Serializable
    @SerialName("Motorcycle")
    data class Motorcycle(
        override val vin: String,
        override val make: String,
        override val model: String,
        override val year: Int,
        override val color: String,
        override val mileage: Int,
        val motoType: MotoType,
    ) : Vehicle()

    @Serializable
    @SerialName("Car")
    data class Car(
        override val vin: String,
        override val make: String,
        override val model: String,
        override val year: Int,
        override val color: String,
        override val mileage: Int,
        val carType: CarType,
    ) : Vehicle()

    @Serializable
    @SerialName("CommercialTransport")
    data class CommercialTransport(
        override val vin: String,
        override val make: String,
        override val model: String,
        override val year: Int,
        override val color: String,
        override val mileage: Int,
        val loadCapacity: Double,
    ) : Vehicle()
}

enum class MotoType {
    CROSS,
    SPORT,
    GRAN_TURISMO
}

enum class CarType {
    SEDAN,
    HATCHBACK,
    ESTATE,
}
