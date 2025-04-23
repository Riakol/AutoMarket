package usecase

import repository.VehicleRepository


class AddVehicleUseCase(private val vehicleRepository: VehicleRepository) {
    fun execute() {
        vehicleRepository.addVehicle()
    }
}