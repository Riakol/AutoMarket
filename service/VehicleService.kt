package org.example.service

import org.example.consoleutils.readDoubleInput
import org.example.consoleutils.readIntInput
import org.example.consoleutils.readStringInput
import org.example.database.JsonDatabase
import org.example.models.*
import org.example.ui.displaySearchCriteria
import org.example.ui.displayVehicleTypeMenu
import kotlin.enums.EnumEntries
import kotlin.enums.enumEntries

fun addVehicle() {
    displayVehicleTypeMenu()

    while (true) {
        val choiceTV = readln().toInt()

        if (choiceTV !in 1..3) {
            println("\nНекорректный выбор. Пожалуйста, выберите число от 1 до 3.")
            continue
        }

        val vin = readStringInput("Введите VIN")
        val make = readStringInput("Введите марку")
        val model = readStringInput("Введите модель")
        val year = readIntInput("Введите год выпуска")
        val color = readStringInput("Введите цвет")
        val mileage = readIntInput("Введите пробег")

        when (choiceTV) {
            1 -> {
                while (true) {
                    displayVehicleType<CarType>()
                    val carType = readln().toInt()

                    if (carType !in 1..CarType.entries.size) {
                        println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${CarType.entries.size}.")
                        continue
                    }
                    JsonDatabase.addVehicle(
                        Car(
                            vin = vin,
                            make = make,
                            model = model,
                            year = year,
                            color = color,
                            mileage = mileage,
                            carType = CarType.entries[carType - 1]
                        )
                    )
                    println("\nАвтомобиль успешно добавлен.\n")
                    return
                }
            }
            2 -> {
                while (true) {
                    displayVehicleType<MotoType>()
                    val motoType = readln().toInt()

                    if (motoType !in 1..CarType.entries.size) {
                        println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${MotoType.entries.size}.")
                        continue
                    }
                    JsonDatabase.addVehicle(
                        Motorcycle(
                            vin = vin,
                            make = make,
                            model = model,
                            year = year,
                            color = color,
                            mileage = mileage,
                            motoType = MotoType.entries[motoType - 1]
                        )
                    )
                    println("\nМотоцикл успешно добавлен.\n")
                    return
                }
            }
            3 -> {
                while (true) {
                    val loadCapacity = readIntInput("Введите грузоподъемность")
                    JsonDatabase.addVehicle(
                        СommercialTransport(
                            vin = vin,
                            make = make,
                            model = model,
                            year = year,
                            color = color,
                            mileage = mileage,
                            loadCapacity = loadCapacity.toDouble()
                        )
                    )
                    println("\nКоммерческий транспорт успешно добавлен.\n")
                    return
                }
            }
        }
    }
}

fun searchListingsMenu() {
    displayVehicleTypeMenu()
    println("4. Общий поиск")
    println("5. Вернуться назад")

    when (val userInput = readln().toInt()) {
        1 -> {
            displaySearchCriteria(userInput)

            while (true) {
                when (val userChoice = readln().toInt()) {
                    1 -> {
                        findVehiclesByCostAndMileage()
                        break
                    }

                    2 -> {
                        findVehiclesByColor()
                        break
                    }

                    3 -> {
                        displayVehicleType<CarType>()
                        findVehiclesByType<CarType>()
                        break
                    }

                    4 -> break
                }
            }
        }

        2 -> {
            displaySearchCriteria(userInput)

            while (true) {
                when (val userChoice = readln().toInt()) {
                    1 -> {
                        findVehiclesByCostAndMileage()
                        break
                    }

                    2 -> {
                        findVehiclesByColor()
                        break
                    }

                    3 -> {
                        displayVehicleType<MotoType>()
                        findVehiclesByType<MotoType>()
                        break
                    }

                    4 -> break
                }
            }
        }

        3 -> {
            displaySearchCriteria(userInput)

            while (true) {
                when (val userChoice = readln().toInt()) {
                    1 -> {
                        findVehiclesByCostAndMileage()
                        break
                    }

                    2 -> {
                        findVehiclesByColor()
                        break
                    }

                    3 -> {
                        val userLoadCapacity = readDoubleInput("\nВведите грузоподъемность")
                        val vehicleType =
                            JsonDatabase.searchVehicleByCapacity(userLoadCapacity)

                        if (vehicleType.isEmpty()) println("Объявлений нет")

                        for (tv in vehicleType) {
                            displayAd(tv)
                        }
                        break
                    }
                    4 -> break
                }
            }
        }

        4 -> {
            val ads = JsonDatabase.getAds()
            for (ad in ads) {
                displayAd(ad)
            }
        }

        5 -> {
            return
        }
    }
}

fun findVehiclesByCostAndMileage() {
    val userPrice = readDoubleInput("Введите стоимость ТС")
    val userMileage = readIntInput("Введите пробег")
    val result = JsonDatabase.searchVehicleByPriceAndMileage(userPrice, userMileage)

    if (result.isEmpty()) {
        println("Объявлений нет\n")
        return
    }

    for (tv in result) {
        displayAd(tv)
    }
}

fun findVehiclesByColor() {
    val userColor = readStringInput("Введите цвет")
    val vehicleColor = JsonDatabase.searchVehicleByColor(userColor)

    if (vehicleColor.isEmpty()) {
        println("Объявлений нет\n")
        return
    }

    for (tv in vehicleColor) {
        displayAd(tv)
    }
}

inline fun <reified T : Enum<T>> findVehiclesByType() {
    val userVehicleType = readln().toInt()
    val vehicleTypes = enumValues<T>()

    while (true) {
        if (userVehicleType !in 1..vehicleTypes.size) {
            println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${vehicleTypes.size}.")
            continue
        }

        val vehicleType = JsonDatabase.searchVehicleByType(vehicleTypes[userVehicleType - 1].name)

        if (vehicleType.isEmpty()) {
            println("Объявлений нет")
            break
        }

        vehicleType.forEach { displayAd(it) }
        break
    }
}

inline fun <reified T : Enum<T>> displayVehicleType() {
    val vehicleTypes = enumEntries<T>()

    println("Выберите тип транспорта по номеру:")
    vehicleTypes.forEachIndexed { index, type ->
        println("${index + 1}. $type")
    }
}


fun displayVehicleDetails(vehicle: Vehicle) {
    when (vehicle) {
        is Car -> println(" * Car Type: ${vehicle.carType}\n")
        is Motorcycle -> println(" * Motorcycle Type: ${vehicle.motoType}\n")
        is СommercialTransport -> println(" * Load Capacity: ${vehicle.loadCapacity.toInt()}\n")
    }
}