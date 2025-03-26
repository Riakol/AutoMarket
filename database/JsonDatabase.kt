package org.example.database

import kotlinx.serialization.json.Json
import org.example.models.*
import java.io.File

object JsonDatabase {
    private val dbFile = File("database.json")

    private val json = Json {
        prettyPrint = true
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }

    var db: Database = if (dbFile.exists()) {
        json.decodeFromString(dbFile.readText())
    } else {
        Database()
    }

    private fun saveDatabase() {
        dbFile.writeText(json.encodeToString(db))
    }

    fun addOwner(owner: Owner) {
        if (db.owners.any { it.id == owner.id }) {
            println("Владелец с id ${owner.id} уже существует!")
            return
        }

        val newOwners = db.owners.toMutableList().apply { add(owner) }
        db = db.copy(owners = newOwners)
        saveDatabase()
    }

    fun addAd(ad: Ad) {
        if (db.ads.any { it.id == ad.id }) {
            println("Объявление с id ${ad.id} уже существует!")
            return
        }

        val newAds = db.ads.toMutableList().apply { add(ad) }
        db = db.copy(ads = newAds)
        saveDatabase()
        println("\nОбъявление успешно добавлено.\n")
    }

    fun editAd(adId: Int, newPrice: Double) {
        val adIndex = db.ads.indexOfFirst { it.id == adId }
        if (adIndex == -1) {
            println("Объявление с ID $adId не найдено.")
            return
        }

        val ad = db.ads[adIndex]

        val updatedAd = ad.copy(
            price = newPrice,
            priceHistory = (ad.priceHistory ?: emptyList()) + newPrice
        )

        val newAds = db.ads.toMutableList().apply { set(adIndex, updatedAd) }
        db = db.copy(ads = newAds)

        saveDatabase()
        println("Объявление №$adId успешно изменено. Новая цена: ${newPrice.toInt()}\n")
    }

    fun archiveAd(adId: Int, cancellationReason: String, isActive: Boolean) {
        val adIndex = db.ads.indexOfFirst { it.id == adId }
        if (adIndex == -1) {
            println("Объявление с id $adId не существует!")
            return
        }

        val ad = db.ads[adIndex]
        val updatedAd = ad.copy(
            reasonForCancellation = cancellationReason,
            isActive = isActive
            )

        val newAds = db.ads.toMutableList().apply { set(adIndex, updatedAd) }
        db = db.copy(ads = newAds)

        saveDatabase()
        println("Объявление с id $adId успешно архивировано.\n")
    }

    fun searchOwnerById(id: Int): Owner {
        return db.owners.filter { it.id == id}[0]
    }

    fun searchByVin(vin: String): Vehicle {
        return db.vehicles.filter {it.vin == vin}[0]
    }

    fun getOwners(): List<Owner> {
        return db.owners
    }

    fun getAds(): List<Ad> {
        return db.ads.filter { it.isActive }
    }

    fun getMaxIndexOwner(): Int {
        return if (db.owners.isEmpty()) 1 else db.owners.maxOf { it.id } + 1
    }

    fun getMaxIndexAd(): Int {
        return if (db.ads.isEmpty()) 1 else db.ads.maxOf { it.id } + 1
    }

    fun addVehicle(vehicle: Vehicle) {
        if (db.vehicles.any { it.vin == vehicle.vin }) {
            println("Транспортное средство с vin ${vehicle.vin} уже существует!")
            return
        }

        val newVehicles = db.vehicles.toMutableList().apply { add(vehicle) }
        db = db.copy(vehicles = newVehicles)
        saveDatabase()
    }

    fun searchVehicleByColor(query: String, numVehicleType: Int): List<Ad> {
        return db.ads.filter { ad ->
            db.vehicles.any { vehicle ->
                vehicle.vin == ad.vin &&
                vehicle.color.contains(query, ignoreCase = true) &&
                when (numVehicleType) {
                    1 -> vehicle is Car
                    2 -> vehicle is Motorcycle
                    else -> vehicle is СommercialTransport
                }
            }
        }
    }

    fun searchVehicleByPriceAndMileage(price: Double, mileage: Int, numVehicleType: Int): List<Ad> {
        return db.ads.filter { ad ->
            ad.price == price && db.vehicles.any { vehicle ->
                vehicle.vin == ad.vin &&
                vehicle.mileage == mileage &&
                when(numVehicleType) {
                    1 -> vehicle is Car
                    2 -> vehicle is Motorcycle
                    else -> vehicle is СommercialTransport
                }
            }
        }
    }

    fun searchVehicleByType(tvType: String): List<Ad> {
        return db.ads.filter { ad ->
            db.vehicles.find { tv -> tv.vin == ad.vin }?.let { vehicle ->
                when (vehicle) {
                    is Car -> vehicle.carType.name == tvType
                    is Motorcycle -> vehicle.motoType.name == tvType
                    else -> false
                }
            } ?: false
        }
    }

    fun searchVehicleByCapacity(tvCapacity: Double): List<Ad> {
        return db.ads.filter { ad ->
            db.vehicles.find { tv -> tv.vin == ad.vin }?.let { vehicle ->
                when (vehicle) {
                    is СommercialTransport -> vehicle.loadCapacity == tvCapacity
                    else -> false
                }
            } ?: false
        }
    }

    fun getVehiclesWithoutAds(): List<Vehicle> {
        val ownerAds = db.ads.map { it.vin }
        return db.vehicles.filter { it.vin !in ownerAds }
    }
}