package usecase

import repository.VehicleRepository

class EditAdUseCase(private val vehicleRepository: VehicleRepository) {
    fun execute() {
        vehicleRepository.editAd()
    }
}