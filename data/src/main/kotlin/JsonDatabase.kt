package com.automarket.data

import entity.CarEntity
import entity.CommercialVehicleEntity
import entity.MotorcycleEntity
import entity.VehicleEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File


object JsonDatabase {
    private val dbFile = File("database.json")

    private val module = SerializersModule {
        polymorphic(VehicleEntity::class) {
            subclass(MotorcycleEntity::class)
            subclass(CarEntity::class)
            subclass(CommercialVehicleEntity::class)
        }
    }

    private val json = Json {
        serializersModule = module
        prettyPrint = true
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }

    var db: Database = if (dbFile.exists()) {
        json.decodeFromString(dbFile.readText())
    } else {
        Database()
    }

    fun saveDatabase() {
        dbFile.writeText(json.encodeToString(db))
    }

    fun getNextOwnerId(): Int {
        return if (db.ownerEntities.isEmpty()) 1 else db.ownerEntities.maxOf { it.id } + 1 // максоф находит максимальный айди и прибавляем на 1
    }

    fun getMaxIndexAd(): Int {
        return if (db.adEntities.isEmpty()) 1 else db.adEntities.maxOf { it.id } + 1 // максоф находит максимальный айди и прибавляем на 1
    }
}



