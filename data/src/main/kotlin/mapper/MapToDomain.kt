package mapper

import entity.AdEntity
import entity.OwnerEntity
import entity.VehicleEntity
import models.*

fun mapOwnerEntityToDomain(ownerEntity: OwnerEntity) : Owner {
    return Owner (
        ownerEntity.id,
        ownerEntity.name,
        ownerEntity.email,
        ownerEntity.phone
    )
}

fun mapVehicleEntityToDomain(vehicleEntity: VehicleEntity) : Vehicle {
    return when(vehicleEntity) {
        is VehicleEntity.MotorcycleEntity -> mapMotoEntityToDomain(vehicleEntity)
        is VehicleEntity.CarEntity -> mapCarEntityToDomain(vehicleEntity)
        is VehicleEntity.CommercialVehicleEntity -> mapCommercialCarToDomain(vehicleEntity)
    }
}

private fun mapMotoEntityToDomain(motorcycleEntity: VehicleEntity.MotorcycleEntity) : Vehicle.Motorcycle {
    return Vehicle.Motorcycle(
        motorcycleEntity.vin,
        motorcycleEntity.make,
        motorcycleEntity.model,
        motorcycleEntity.year,
        motorcycleEntity.color,
        motorcycleEntity.mileage,
        MotoType.valueOf(motorcycleEntity.motoType.name),
    )
}

private fun mapCarEntityToDomain(carEntity: VehicleEntity.CarEntity) : Vehicle.Car {
    return Vehicle.Car(
        carEntity.vin,
        carEntity.make,
        carEntity.model,
        carEntity.year,
        carEntity.color,
        carEntity.mileage,
        CarType.valueOf(carEntity.carType.name),
    )
}

private fun mapCommercialCarToDomain(commercialVehicleEntity: VehicleEntity.CommercialVehicleEntity) : Vehicle.CommercialTransport {
    return Vehicle.CommercialTransport (
        commercialVehicleEntity.vin,
        commercialVehicleEntity.make,
        commercialVehicleEntity.model,
        commercialVehicleEntity.year,
        commercialVehicleEntity.color,
        commercialVehicleEntity.mileage,
        commercialVehicleEntity.loadCapacity
    )
}

fun mapAdEntityToDomain(adEntity: AdEntity) : Ad {
    return Ad(
        adEntity.id,
        adEntity.ownerId,
        adEntity.vin,
        adEntity.price,
        adEntity.date
    )
}