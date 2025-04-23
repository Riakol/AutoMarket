package com.automarket.data
import entity.AdEntity
import entity.OwnerEntity
import entity.VehicleEntity
import kotlinx.serialization.Serializable

@Serializable
class Database(
    val ownerEntities: List<OwnerEntity> = emptyList(),
    val adEntities: List<AdEntity> = emptyList(),
    val vehicleEntities: List<VehicleEntity> = emptyList(),
){
    fun copy(
        vehicleEntities: List<VehicleEntity> = this.vehicleEntities,
        ownerEntities: List<OwnerEntity> = this.ownerEntities,
        adEntities: List<AdEntity> = this.adEntities
    ) : Database {
        return Database(
            vehicleEntities = vehicleEntities,
            ownerEntities = ownerEntities,
            adEntities = adEntities
        )
    }
}

