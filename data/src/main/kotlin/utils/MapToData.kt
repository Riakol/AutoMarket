package utils

import entity.AdEntity
import entity.VehicleEntity
import models.Ad
import models.Vehicle

fun mapVehicleToData(vehicle: Vehicle) : VehicleEntity {
    return when(vehicle) {
        is Vehicle.Motorcycle -> mapMotoToData(vehicle)
        is Vehicle.Car -> mapCarToData(vehicle)
        is Vehicle.CommercialTransport -> mapCommercialCarToData(vehicle)
    }
}

private fun mapMotoToData(motorcycle: Vehicle.Motorcycle) : VehicleEntity.MotorcycleEntity {
    return VehicleEntity.MotorcycleEntity(
        motorcycle.vin,
        motorcycle.make,
        motorcycle.model,
        motorcycle.year,
        motorcycle.color,
        motorcycle.mileage,
        entity.MotoType.valueOf(motorcycle.motoType.name),
    )
}

private fun mapCarToData(car: Vehicle.Car) : VehicleEntity.CarEntity {
    return VehicleEntity.CarEntity(
        car.vin,
        car.make,
        car.model,
        car.year,
        car.color,
        car.mileage,
        models.CarType.valueOf(car.carType.name),
    )
}

private fun mapCommercialCarToData(commercialTransport: Vehicle.CommercialTransport) : VehicleEntity.CommercialVehicleEntity {
    return VehicleEntity.CommercialVehicleEntity (
        commercialTransport.vin,
        commercialTransport.make,
        commercialTransport.model,
        commercialTransport.year,
        commercialTransport.color,
        commercialTransport.mileage,
        commercialTransport.loadCapacity
    )
}

fun mapAdToData(ad: Ad) : AdEntity {
    return AdEntity(
        ad.id,
        ad.ownerId,
        ad.vin,
        ad.price,
        ad.date
    )
}