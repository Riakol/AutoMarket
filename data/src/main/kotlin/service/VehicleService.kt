package service

import JsonDatabase.db
import JsonDatabase.saveDatabase
import entity.VehicleEntity

fun addVehicle(vehicle: VehicleEntity) {
    if (db.vehicles.any { it.vin == vehicle.vin }) {
        println("Транспортное средство с vin ${vehicle.vin} уже существует!")
        return
    }

    val newVehicles = db.vehicles.toMutableList().apply { add(vehicle) }
    db = db.copy(vehicles = newVehicles)
    saveDatabase()
}