package usecase

import repository.VehicleRepository


class AddOwnerUseCase(private val vehicleRepository: VehicleRepository) {
        fun execute() {
                vehicleRepository.addOwner()
        }
}
