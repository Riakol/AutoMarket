package usecase

import repository.VehicleRepository


class AddAdUseCase(private val vehicleRepository: VehicleRepository) {
    fun execute() {
        return vehicleRepository.addAd()
    }
}