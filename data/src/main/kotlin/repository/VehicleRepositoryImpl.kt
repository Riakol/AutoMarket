package com.automarket.data.repository

import com.automarket.data.JsonDatabase
import com.automarket.data.JsonDatabase.db
import com.automarket.data.JsonDatabase.saveDatabase
import entity.*
import entity.CarType
import entity.MotoType
import models.*
import repository.VehicleRepository
import java.time.LocalDate


class VehicleRepositoryImpl : VehicleRepository {
    override fun addAd() {
        val getOwners = showOwners()

        while (true) {
            println("Выберите владельца по id:\n")
            for (owner in getOwners) {
                println("${owner.id}. ${owner.name}")
            }
            val userId = readln().toInt()
            val currentOwner = getOwners.find { it.id == userId }
            val ownerVehiclesWithoutAds = getVehiclesWithoutAds()

            if (ownerVehiclesWithoutAds.isEmpty()) {
                if (currentOwner != null) {
                    println("Для владельца ${currentOwner.name} нет транспортных средств без объявлений\n")
                    continue
                }
            }

            println("Выберите ТС по номеру:\n")

            for ((index, vehicle) in ownerVehiclesWithoutAds.withIndex()) {
                print("${index + 1}.\n * ${vehicle.brand} ${vehicle.model}\n * Year: ${vehicle.year}\n * Color: ${vehicle.color}\n * Mileage: ${vehicle.mileage}\n")

                when (vehicle) {
                    is CarEntity -> println(" * CarType: ${vehicle.carType}")
                    is MotorcycleEntity -> println(" * MotoType: ${vehicle.motoType}")
                    is CommercialVehicleEntity -> println(" * Capacity: ${vehicle.capacity}")
                }
            }

            val userSelectedVehicle = readln().toInt()
            val ownerVin = ownerVehiclesWithoutAds[userSelectedVehicle - 1].vin


            println("Введите цену:")
            val price = readln().toInt()

            postAd(
                AdEntity(
                    JsonDatabase.getMaxIndexAd(), userId, ownerVin, price, LocalDate.now().toString()
                )
            )
            break
        }
    }

    override fun addOwner() {
        println("Введите имя:")
        val userName = readln()
        println("Введите телефон:")
        val userPhone = readln()
        println("Введите email:")
        val userMail = readln()

        val ownerEntity = OwnerEntity(
            JsonDatabase.getNextOwnerId(), userName, userPhone, userMail
        )

        val newOwners = db.ownerEntities.toMutableList().apply { add(ownerEntity) }
        db = db.copy(ownerEntities = newOwners)
        saveDatabase()
    }

    override fun addVehicle() {
        println("1.Добавить автомобиль")
        println("2.Добавить мотоцикл")
        println("3.Добавить коммерческий транспорт")
        val userChoice = readln().toInt()
        when (userChoice) {
            1 -> {
                println("Введите vin:")
                val userVin = readln()
                println("Введите бренд:")
                val userBrand = readln()
                println("Введите модель:")
                val userModel = readln()
                println("Введите год:")
                val userYear = readln().toInt()
                println("Ввдите цвет:")
                val userColor = readln()
                println("Введите пробег:")
                val userMileage = readln().toInt()
                println("Введите тип автомобиля:")
                for (ct in CarType.entries) {
                    println("${ct.ordinal + 1}. $ct")
                }
                val userCarType = readln().toInt()
                val userCarEntity = CarEntity(
                    userVin,
                    userBrand,
                    userModel,
                    userYear,
                    userColor,
                    userMileage,
                    CarType.entries[userCarType - 1]
                )
                saveVehicle(userCarEntity)

            }

            2 -> {
                println("Введите vin:")
                val userVin = readln()
                println("Введите бренд:")
                val userBrand = readln()
                println("Введите модель:")
                val userModel = readln()
                println("Введите год:")
                val userYear = readln().toInt()
                println("Ввдите цвет:")
                val userColor = readln()
                println("Введите пробег:")
                val userMileage = readln().toInt()
                println("Введите тип мотоцикла:")
                for (mt in MotoType.entries) {
                    println("${mt.ordinal + 1}. $mt")
                }
                val userMotoType = readln().toInt()
                val userMoto = MotorcycleEntity(
                    userVin,
                    userBrand,
                    userModel,
                    userYear,
                    userColor,
                    userMileage,
                    MotoType.entries[userMotoType - 1]
                )
                saveVehicle(userMoto)
            }

            3 -> {
                println("Введите vin:")
                val userVin = readln()
                println("Введите бренд:")
                val userBrand = readln()
                println("Введите модель:")
                val userModel = readln()
                println("Введите год:")
                val userYear = readln().toInt()
                println("Ввдите цвет:")
                val userColor = readln()
                println("Введите пробег:")
                val userMileage = readln().toInt()
                println("Введите грузоподъемность:")
                val userCapacity = readln().toInt()
                val userCommercialVehicleEntity = CommercialVehicleEntity(
                    userVin, userBrand, userModel, userYear, userColor, userMileage, userCapacity
                )
                saveVehicle(userCommercialVehicleEntity)
            }
        }
    }

    override fun removeAds() {
        val ads = getAds()

        if (ads.isEmpty()) {
            println("Нет объявлений.")
            return
        }
        println("Выберите номер объявления: \n")
        for ((index, ad) in ads.withIndex()) {
            val vinTV = searchByVin(ad.vin)
            val ownerID = searchOwnerById(ad.ownerId)
            println("Объявления №${ad.id} от ${ad.date}")
            println("Продавец ${ownerID.name}")
            println("Бренд ${vinTV.brand} ${vinTV.model}")
            println("Год ${vinTV.year}")
            println("Цвет ${vinTV.color}")
            println("Пробег ${vinTV.mileage}")
            when (vinTV) {
                is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

            }
        }
        val userAdInput = readln().toIntOrNull()
        println("Укажите причину снятия объявления: ")

        val userCancelAd = readln()
        if (userAdInput != null) {
            removeAd(
                userAdInput, userCancelAd, false
            )
        }
    }

    override fun searchAds() {
        while (true) {
            println("Выберите тип ТС:")
            println("1. Автомобиль")
            println("2. Мотоцикл")
            println("3. Коммерческий транспорт")
            println("4. Назад")

            val userVehicleMenu = readln().toInt()



            when (userVehicleMenu) {
                1 -> {
                    println("Выберите критерии поиска по номеру:")
                    println("1. С определенной стоимостью и пробегом")
                    println("2. С определенным цветом")
                    println("3. С определнным типом")
                    val userCriteriaInput = readln().toInt()

                    when (userCriteriaInput) {
                        1 -> {
                            println("Введите стоимость автомобиля:")
                            val userPrice = readln().toInt()
                            println("Введите пробег:")
                            val userMileage = readln().toInt()
                            val userCarList = findVehicleByPriceAndMileage(
                                userPrice, userMileage, userVehicleMenu
                            )
                            for ((index, ad) in userCarList.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

                                }
                            }
                        }

                        2 -> {
                            println("Введите цвет:")
                            val userColor = readln()
                            val userGetList = findVehicleByColor(
                                userColor, userVehicleMenu
                            )

                            for ((index, ad) in userGetList.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

                                }
                            }
                        }

                        3 -> {
                            println("Выберите тип по номеру: \n")

                            for (type in CarType.entries) {
                                println("${type.ordinal + 1}. ${type}")
                            }
                            val userType = readln().toInt()
                            val userChoiceType = findByType(
                                CarType.entries[userType - 1].name
                            )

                            for ((index, ad) in userChoiceType.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")
                                }
                            }
                        }
                    }
                }

                2 -> {
                    println("Выберите критерии поиска по номеру:")
                    println("1. С определенной стоимостью и пробегом")
                    println("2. С определенным цветом")
                    println("3. С определнным типом")
                    val userCriteriaInput = readln().toInt()

                    when (userCriteriaInput) {
                        1 -> {
                            println("Введите стоимость мотоцикла:")
                            val userPrice = readln().toInt()
                            println("Введите пробег:")
                            val userMileage = readln().toInt()
                            val userMotoList = findVehicleByPriceAndMileage(
                                userPrice, userMileage, userVehicleMenu
                            )
                            for ((index, ad) in userMotoList.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

                                }
                            }
                        }

                        2 -> {
                            println("Введите цвет:")
                            val userColor = readln()
                            val userGetList = findVehicleByColor(
                                userColor, userVehicleMenu
                            )

                            for ((index, ad) in userGetList.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

                                }
                            }
                        }

                        3 -> {
                            println("Выберите тип по номеру: \n")

                            for (type in MotoType.entries) {
                                println("${type.ordinal + 1}. ${type}")
                            }
                            val userType = readln().toInt()
                            val userChoiceType = findByType(
                                MotoType.entries[userType - 1].name
                            )

                            for ((index, ad) in userChoiceType.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")
                                }
                            }
                        }
                    }
                }

                3 -> {
                    println("Выберите критерии поиска по номеру:")
                    println("1. С определенной стоимостью и пробегом")
                    println("2. С определенным цветом")
                    println("3. С определённым тоннажем")
                    val userCriteriaInput = readln().toInt()

                    when (userCriteriaInput) {
                        1 -> {
                            println("Введите стоимость коммерческого транспорта:")
                            val userPrice = readln().toInt()
                            println("Введите пробег:")
                            val userMileage = readln().toInt()
                            val userCarList = findVehicleByPriceAndMileage(
                                userPrice, userMileage, userVehicleMenu
                            )
                            for ((index, ad) in userCarList.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

                                }
                            }
                        }

                        2 -> {
                            println("Введите цвет:")
                            val userColor = readln()
                            val userGetList = findVehicleByColor(
                                userColor, userVehicleMenu
                            )

                            for ((index, ad) in userGetList.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")

                                }
                            }
                        }

                        3 -> {
                            println("Введите грузоподъёмность:\n")

                            val userCapacity = readln().toInt()
                            val userGetListByCapacity = findByCapacity(userCapacity)

                            for ((index, ad) in userGetListByCapacity.withIndex()) {
                                val vinTV = searchByVin(ad.vin)
                                val ownerID = searchOwnerById(ad.ownerId)
                                println("Объявления №${ad.id} от ${ad.date}")
                                println("Продавец ${ownerID.name}")
                                println("Бренд ${vinTV.brand} ${vinTV.model}")
                                println("Год ${vinTV.year}")
                                println("Цвет ${vinTV.color}")
                                println("Пробег ${vinTV.mileage}")
                                when (vinTV) {
                                    is CarEntity -> println("Тип автомобиля: ${vinTV.carType} \n")
                                    is MotorcycleEntity -> println("Тип мотоцикла: ${vinTV.motoType} \n")
                                    is CommercialVehicleEntity -> println("Грузоподъемность: ${vinTV.capacity} \n")
                                }
                            }
                        }
                    }
                }

                4 -> {
                    break
                }

            }
        }
    }

    private fun saveVehicle(vehicleEntity: VehicleEntity) {
        val newVehicle = db.vehicleEntities.toMutableList() .apply { add(vehicleEntity) }
        db = db.copy(vehicleEntities = newVehicle)
        saveDatabase()
    }

    private fun postAd(adEntity: AdEntity) {
        if (db.adEntities.any { it.id == adEntity.id }) {
            println("Объявление с id ${adEntity.id} уже существует!")
            return
        }

        val newAds = db.adEntities.toMutableList().apply { add(adEntity) }
        db = db.copy(adEntities = newAds)
        saveDatabase()
        println("\nОбъявление успешно добавлено.\n")
    }

    private fun removeAd(adId: Int, cancellationReason: String, isActive: Boolean) {
        val adIndex = db.adEntities.indexOfFirst { it.id == adId }
        if (adIndex == -1) {
            println("Объявление с id $adId не существует!")
            return
        }

        val ad = db.adEntities[adIndex]
        val updatedAd = ad.copy(
            reasonForCancellation = cancellationReason,
            isActive = isActive
        )

        val newAds = db.adEntities.toMutableList().apply { set(adIndex, updatedAd) }
        db = db.copy(adEntities = newAds)

        saveDatabase()
        println("Объявление с id $adId успешно архивировано.\n")
    }

    override fun findByCapacity(tvCapacity: Int): List<Ad> {
        return db.adEntities.filter { ad ->
            db.vehicleEntities.any { vehicle ->
                vehicle.vin == ad.vin &&
                        when(vehicle) {
                            is CommercialVehicleEntity -> vehicle.capacity == tvCapacity
                            else -> false
                        }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun findByType(vehicleType: String): List<Ad> {
        return db.adEntities.filter { ad ->
            db.vehicleEntities.any { vehicle ->
                vehicle.vin == ad.vin &&
                        when(vehicle) {
                            is CarEntity -> vehicle.carType.name == vehicleType
                            is MotorcycleEntity -> vehicle.motoType.name == vehicleType
                            else -> false
                        }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun findVehicleByColor(color: String, typeVehicle: Int): List<Ad> {
        return db.adEntities.filter { ad ->
            db.vehicleEntities.any { vehicle ->
                vehicle.color == color &&
                        ad.vin == vehicle.vin && when(typeVehicle) {
                    1 -> vehicle is CarEntity
                    2 -> vehicle is MotorcycleEntity
                    else -> vehicle is CommercialVehicleEntity
                }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun findVehicleByPriceAndMileage(price: Int, mileage: Int, typeVehicle: Int): List<Ad> {
        return db.adEntities.filter { ad ->
            ad.price == price && db.vehicleEntities.any { vehicle ->
                vehicle.vin == ad.vin &&
                        vehicle.mileage == mileage &&
                        when(typeVehicle) {
                            1 -> vehicle is CarEntity
                            2 -> vehicle is MotorcycleEntity
                            else -> vehicle is CommercialVehicleEntity
                        }
            }
        }
            .map { adEntity -> mapAdEntityToDomain(adEntity) }
    }

    override fun searchOwnerById(id: Int): Owner {
        return mapOwnerEntityToDomain(db.ownerEntities.filter { owner -> owner.id == id }[0])
    }

    override fun searchByVin(vin: String): models.Vehicle {
        return mapVehicleEntityToDomain(db.vehicleEntities.filter { it.vin == vin }[0])
    }

    override fun getAds(): List<Ad> {
        return db.adEntities.filter { it.isActive }
            .map {  mapAdEntityToDomain(it) }
    }

    override fun getVehiclesWithoutAds(): List<Vehicle> {
        val ownerAds = db.adEntities.map { it.vin } //проходимся по обьяв и находим все вин авто
        return db.vehicleEntities.filter { it.vin !in ownerAds }
            .map { mapVehicleEntityToDomain(it) } // у нас есть обьяв где есть vin автомобилей(мы просим вернуть все авто которых нет в обьяв)
    }

    override fun showOwners(): List<Owner> {
        return db.ownerEntities
            .map { mapOwnerEntityToDomain(it) }
    }

    private fun mapAdEntityToDomain(adEntity: AdEntity) : Ad {
        return Ad(
            adEntity.id,
            adEntity.ownerId,
            adEntity.vin,
            adEntity.price,
            adEntity.date
        )
    }

//    private fun mapListAdEntityToDomain(adEntity: AdEntity) : List<Ad> {
//        return listOf(
//            Ad(
//                adEntity.id,
//                adEntity.ownerId,
//                adEntity.vin,
//                adEntity.price,
//                adEntity.date
//            )
//        )
//    }

    private fun mapOwnerEntityToDomain(ownerEntity: OwnerEntity) : Owner {
        return Owner (
            ownerEntity.id,
            ownerEntity.name,
            ownerEntity.email,
            ownerEntity.phone
        )
    }

    private fun mapVehicleEntityToDomain(vehicleEntity: VehicleEntity) : Vehicle {
        return when(vehicleEntity) {
            is MotorcycleEntity -> mapMotoEntityToDomain(vehicleEntity)
            is CarEntity -> mapCarEntityToDomain(vehicleEntity)
            is CommercialVehicleEntity -> mapCommercialCarToDomain(vehicleEntity)
            else -> throw IllegalArgumentException("Неизвестный тип транспортного средства")
        }
    }

    private fun mapMotoEntityToDomain(motorcycleEntity: MotorcycleEntity) : Motorcycle {
       return Motorcycle (
            motorcycleEntity.vin,
            motorcycleEntity.brand,
            motorcycleEntity.model,
            motorcycleEntity.year,
            motorcycleEntity.color,
            motorcycleEntity.mileage,
           models.MotoType.valueOf(motorcycleEntity.motoType.name),
        )
    }

    private fun mapCarEntityToDomain(carEntity: CarEntity) : Car {
        return Car (
            carEntity.vin,
            carEntity.brand,
            carEntity.model,
            carEntity.year,
            carEntity.color,
            carEntity.mileage,
            models.CarType.valueOf(carEntity.carType.name),
        )
    }

    private fun mapCommercialCarToDomain(commercialVehicleEntity: CommercialVehicleEntity) : CommercialVehicle {
        return CommercialVehicle (
            commercialVehicleEntity.vin,
            commercialVehicleEntity.brand,
            commercialVehicleEntity.model,
            commercialVehicleEntity.year,
            commercialVehicleEntity.color,
            commercialVehicleEntity.mileage,
            commercialVehicleEntity.capacity
        )
    }
}