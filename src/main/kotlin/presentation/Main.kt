package presentation

import com.automarket.data.repository.VehicleRepositoryImpl
import repository.VehicleRepository
import usecase.*
import utils.displayMenu

fun main() {

    val vehicleRepository: VehicleRepository = VehicleRepositoryImpl()

    while (true) {
        displayMenu()
        val choiceMain = readln().toIntOrNull()

        if (choiceMain == null || choiceMain !in 1..6) {
            println("Некорректный выбор. Пожалуйста, выберите число от 1 до 6.\n")
            continue
        }

        while (true) {
            when (choiceMain) {
                1 -> {
                    AddOwnerUseCase(vehicleRepository).execute(); break }
                2 -> {
                    AddVehicleUseCase(vehicleRepository).execute(); break }
                3 -> {
                    AddAdUseCase(vehicleRepository).execute(); break }
                4 -> {
                    RemoveAdsUseCase(vehicleRepository).execute(); break }
                5 -> {
                    EditAdUseCase(vehicleRepository).execute(); break }
                6 -> {
                    SearchAdsUseCase(vehicleRepository).execute(); break }
            }
        }
    }
}
