package models

import java.util.*


abstract class Vehicle {
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


class Motorcycle(
    override val vin: String,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val color: String,
    override val mileage: Int,
    val motoType: MotoType,
): Vehicle() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Motorcycle) return false
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


class Car(
    override val vin: String,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val color: String,
    override val mileage: Int,
    val carType: CarType,
): Vehicle() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Car) return false
        return this.vin == other.vin && this.mileage == other.mileage
    }

    override fun hashCode(): Int {
        return Objects.hash(vin, mileage);
    }

    override fun toString(): String {
        return "Car(vin = $vin, brand = $brand, model = $model, year = $year, color = $color, mileage = $mileage)"
    }


}


class CommercialVehicle(
    override val vin: String,
    override val brand: String,
    override val model: String,
    override val year: Int,
    override val color: String,
    override val mileage: Int,
    val capacity: Int,
): Vehicle() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CommercialVehicle) return false
        return this.vin == other.vin && this.mileage == other.mileage
    }

    override fun hashCode(): Int {
        return Objects.hash(vin, mileage);
    }

    override fun toString(): String {
        return "CommercialVehicle(vin = $vin, brand = $brand)"
    }
}