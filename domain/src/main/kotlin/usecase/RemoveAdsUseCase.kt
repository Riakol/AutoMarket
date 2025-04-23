package usecase

import repository.VehicleRepository


class RemoveAdsUseCase(private val vehicleRepository: VehicleRepository) {
    fun execute() {
        vehicleRepository.removeAds()
    }
}