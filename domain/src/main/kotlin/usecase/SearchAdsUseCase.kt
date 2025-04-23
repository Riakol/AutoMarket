package usecase

import repository.VehicleRepository

class SearchAdsUseCase(private val vehicleRepository: VehicleRepository) {
    fun execute() {
        vehicleRepository.searchAds()
    }
}
