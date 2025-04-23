package repository

import models.Ad
import models.Owner
import models.Vehicle

interface VehicleRepository {
    fun addAd()
    fun addOwner()
    fun addVehicle()
    fun removeAds()
    fun searchAds()
    fun editAd()
    fun findByCapacity(tvCapacity: Int) : List<Ad>
    fun findByType(vehicleType: String): List<Ad>
    fun findVehicleByColor(color: String, typeVehicle: Int): List<Ad>
    fun findVehicleByPriceAndMileage(price: Int, mileage: Int, typeVehicle: Int ): List<Ad>
    fun searchOwnerById(id: Int): Owner
    fun searchByVin(vin: String): Vehicle
    fun getAds(): List<Ad>
    fun getVehiclesWithoutAds(): List<Vehicle>
    fun showOwners(): List<Owner>
}