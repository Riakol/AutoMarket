package com.automarket.data.repository

import JsonDatabase
import JsonDatabase.db
import entity.AdEntity
import entity.MotoType
import entity.OwnerEntity
import entity.VehicleEntity
import mapper.*
import models.Ad
import models.CarType
import models.Owner
import models.Vehicle
import org.example.consoleutils.readDoubleInput
import org.example.consoleutils.readIntInput
import org.example.consoleutils.readStringInput
import repository.VehicleRepository
import utils.*
import java.time.LocalDate


class VehicleRepositoryImpl : VehicleRepository {
    override fun addAd() {
        val getOwners = showOwners()
        val showVehicles = getVehiclesWithoutAds()
        var ownerInfo = mapOwnerEntityToDomain(OwnerEntity(0, "", "", ""))
        var ownerVin: String = ""
        var ownerPrice: Double = 0.0

        while (true) {
            println("Выберите владельца по id:\n")

            for (owner in getOwners) println("${owner.id}. ${owner.name}")
            val ownerInput = readln().toIntOrNull()

            val selectedOwner = getOwners.find { ownerInput != null && it.id == ownerInput }
            if (selectedOwner == null) {
                println("Владелец с id $ownerInput не найден. Попробуйте снова.\n")
                continue
            }
            ownerInfo =  selectedOwner
            break
        }

        if (showVehicles.isEmpty()) {
            println("Для владельца ${ownerInfo.name} нет транспортных средств без объявления.\n")
            return
        }

        while (true) {
            println("Выберите ТС по номеру:")

            for ((index, vehicle) in showVehicles.withIndex()) {
                val mappingVehicleToData = mapVehicleToData(vehicle)
                print("${index + 1}. ")
                displaySellVehicleType(mappingVehicleToData)
                println("* ${vehicle.make} ${vehicle.model}")
                println("* Year: ${vehicle.year}")
                println("* Color: ${vehicle.color}")
                println("* Mileage: ${vehicle.mileage}")
                displayVehicleDetails(mappingVehicleToData)
            }

            val userInput = readln().toIntOrNull()
            if (userInput !in 1..showVehicles.size) {
                println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${showVehicles.size}.")
                continue
            }
            if (userInput != null) {
                ownerVin = showVehicles[userInput - 1].vin
            }
            break
        }

        while (true) {
            val userPriceInput = readDoubleInput("Введите цену")
            ownerPrice = userPriceInput
            break
        }

        val currentDate = LocalDate.now().toString()

        service.postAd(
            AdEntity(
                JsonDatabase.getNextIdAd(),
                ownerId = ownerInfo.id,
                vin = ownerVin,
                price = ownerPrice,
                date = currentDate,
            )
        )
        return
    }

    override fun addOwner() {
        val name = readStringInput("Введите имя владельца")
        val phone = readStringInput("Введите номер телефона владельца")
        val email = readStringInput("Введите электронную почту владельца")

        service.addOwner(
            OwnerEntity(
                id = JsonDatabase.getNextIdOwner(),
                name = name,
                phone = phone,
                email = email
            )
        )
        println("\n$name успешно добавлен.\n")
    }


    override fun addVehicle() {
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
                        service.addVehicle(
                            VehicleEntity.CarEntity(
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

                        if (motoType !in 1..MotoType.entries.size) {
                            println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${MotoType.entries.size}.")
                            continue
                        }
                        service.addVehicle(
                            VehicleEntity.MotorcycleEntity(
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
                        service.addVehicle(
                            VehicleEntity.CommercialVehicleEntity(
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

    override fun editAd() {
        val ads = getAds()

        if (ads.isEmpty()) {
            println("Нет объявлений.")
            return
        }

        println("Выберите номер объявления:\n")

        for (ad in ads) {
            displayAd(mapAdToData(ad))
        }

        val userInput = readln()

        while (true) {
            when {
                db.ads.none { it.id == userInput.toInt() && it.isActive } -> {
                    println("Объявления с id $userInput не существует. Пожалуйста, выберите корректный id")
                    if (userInput.all { it.isDigit() }) continue else break
                }
                else -> {
                    val newPrice = readDoubleInput("Укажите новую цену")

                    service.configAd(userInput.toInt(), newPrice)
                    return
                }
            }
        }
    }

    override fun removeAds() {
        val ads = getAds()

        if (ads.isEmpty()) {
            println("Нет объявлений.")
            return
        }

        println("Выберите номер объявления:\n")

        for (ad in ads) {
            displayAd(mapAdToData(ad))
        }

        var userInput = readln()

        while (true) {
            when {
                db.ads.none { it.id == userInput.toInt() && it.isActive } -> {
                    println("Объявления с id $userInput не существует. Пожалуйста, выберите корректный id")
                    userInput = readln()
                    if (userInput.all { it.isDigit() }) continue else break
                }
                else -> {
                    println("Укажите причину снятия объявления:")
                    val reason = readln()
                    service.archiveAd(userInput.toInt(), reason, false)
                    return
                }
            }
        }
    }

    override fun searchAds() {
        displayVehicleTypeMenu()
        println("4. Общий поиск")
        println("5. Вернуться назад")

        var userInput: Int? = null
        do {
            userInput = readln().toIntOrNull() ?: continue
            if (userInput !in 1..5) {
                println("Некорректный выбор. Пожалуйста, выберите число от 1 до 5.")
            }
        } while (userInput !in 1..5)

        when (userInput) {
            1, 2, 3 -> {
                displaySearchCriteria(userInput)
                saveVehicleTypeSelection(userInput)
            }
            4 -> {
                val ads = getAds()
                for (ad in ads) {
                    displayAd(mapAdToData(ad))
                }
            }
            5 -> {
                return
            }
        }
    }


    override fun findByCapacity(tvCapacity: Int): List<Ad> {
        return db.ads.filter { ad ->
            db.vehicles.find { tv -> tv.vin == ad.vin }?.let { vehicle ->
                vehicle is VehicleEntity.CommercialVehicleEntity && vehicle.loadCapacity.toInt() == tvCapacity
            } ?: false
        }.map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    private fun findVehiclesByCostAndMileage(numVehicleType: Int) {
        val userPrice = readDoubleInput("Введите стоимость ТС")
        val userMileage = readIntInput("Введите пробег")
        val result = findVehicleByPriceAndMileage(userPrice.toInt(), userMileage, numVehicleType)

        if (result.isEmpty()) {
            println("Объявлений нет\n")
            return
        }

        for (tv in result) {
            displayAd(mapAdToData(tv))
        }
    }

    private fun findVehiclesByColor(num: Int) {
        val userColor = readStringInput("Введите цвет\n")
        val vehicleColor = findVehicleByColor(userColor, num)

        if (vehicleColor.isEmpty()) {
            println("Объявлений нет\n")
            return
        }

        for (tv in vehicleColor) {
            displayAd(mapAdToData(tv))
        }
    }

    private fun saveVehicleTypeSelection(userInput: Int) {
        while (true) {
            when (readln().toInt()) {
                1 -> {
                    findVehiclesByCostAndMileage(userInput)
                    break
                }
                2 -> {
                    findVehiclesByColor(userInput)
                    break
                }
                3 -> {
                    when(userInput) {
                        1 -> {
                            displayVehicleType<CarType>()
                            findVehiclesByType<CarType>()
                            break
                        }
                        2 -> {
                            displayVehicleType<MotoType>()
                            findVehiclesByType<MotoType>()
                            break
                        }
                        3 -> {
                            val userLoadCapacity = readDoubleInput("\nВведите грузоподъемность")
                            val vehicleType =
                                findByCapacity(userLoadCapacity.toInt())

                            if (vehicleType.isEmpty()) println("Объявлений нет")

                            for (tv in vehicleType) {
                                displayAd(mapAdToData(tv))
                            }
                            break
                        }
                    }
                }
                4 -> break
            }
        }
    }

    override fun findByType(vehicleType: String): List<Ad> {
        return db.ads.filter { ad ->
            db.vehicles.any { vehicle ->
                vehicle.vin == ad.vin &&
                        when(vehicle) {
                            is VehicleEntity.CarEntity -> vehicle.carType.name == vehicleType
                            is VehicleEntity.MotorcycleEntity -> vehicle.motoType.name == vehicleType
                            else -> false
                        }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun findVehicleByColor(color: String, typeVehicle: Int): List<Ad> {
        return db.ads.filter { ad ->
            db.vehicles.any { vehicle ->
                vehicle.color == color &&
                        ad.vin == vehicle.vin && when(typeVehicle) {
                    1 -> vehicle is VehicleEntity.CarEntity
                    2 -> vehicle is VehicleEntity.MotorcycleEntity
                    else -> vehicle is VehicleEntity.CommercialVehicleEntity
                }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun findVehicleByPriceAndMileage(price: Int, mileage: Int, typeVehicle: Int): List<Ad> {
        return db.ads.filter { ad ->
            ad.price.toInt() == price && db.vehicles.any { vehicle ->
                vehicle.vin == ad.vin &&
                        vehicle.mileage == mileage &&
                        when(typeVehicle) {
                            1 -> vehicle is VehicleEntity.CarEntity
                            2 -> vehicle is VehicleEntity.MotorcycleEntity
                            else -> vehicle is VehicleEntity.CommercialVehicleEntity
                        }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun searchOwnerById(id: Int): Owner {
        return mapOwnerEntityToDomain(db.owners.filter { it.id == id }[0])
    }

    override fun searchByVin(vin: String): models.Vehicle {
        return mapVehicleEntityToDomain(db.vehicles.filter { it.vin == vin }[0])
    }

    override fun getAds(): List<Ad> {
        return db.ads.filter { it.isActive }
            .map { mapAdEntityToDomain(it) }
    }

    override fun getVehiclesWithoutAds(): List<Vehicle> {
        val ownerAds = db.ads.map { it.vin }
        return db.vehicles.filter { it.vin !in ownerAds }
            .map { mapVehicleEntityToDomain(it) }
    }

    override fun showOwners(): List<Owner> {
        return db.owners
            .map { mapOwnerEntityToDomain(it) }
    }

    private fun displayAd(ad: AdEntity) {
        val vinTv = searchByVin(ad.vin)
        val ownerName = searchOwnerById(ad.ownerId)
        val priceHistoryString = ad.priceHistory
            ?.takeIf { it.size > 1 }
            ?.dropLast(1)
            ?.joinToString(", ") { it.toInt().toString() }
            ?: ""

        println("Объявление №${ad.id} от ${ad.date}")
        displaySellVehicleType(mapVehicleToData(vinTv))
        println("Seller: ${ownerName.name}, ${ownerName.phone}")
        println("* ${vinTv.make} ${vinTv.model}")
        println("* Year: ${vinTv.year}")
        println("* Price: ${ad.price.toInt()}")
        if (priceHistoryString.isNotEmpty()) println("* Price change history: $priceHistoryString")
        println("* Color: ${vinTv.color}")
        println("* Mileage: ${vinTv.mileage}")
        displayVehicleDetails(mapVehicleToData(vinTv))
    }

    private inline fun <reified T : Enum<T>> findVehiclesByType() {
        val userVehicleType = readln().toInt()
        val vehicleTypes = enumValues<T>()

        while (true) {
            if (userVehicleType !in 1..vehicleTypes.size) {
                println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${vehicleTypes.size}.")
                continue
            }

            val vehicleType = findByType(vehicleTypes[userVehicleType - 1].name)

            if (vehicleType.isEmpty()) {
                println("Объявлений нет")
                break
            }

            vehicleType.forEach { displayAd(mapAdToData(it)) }
            break
        }
    }
}