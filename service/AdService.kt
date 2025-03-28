package org.example.service

import org.example.consoleutils.readDoubleInput
import org.example.database.JsonDatabase
import org.example.database.JsonDatabase.db
import org.example.models.*
import java.time.LocalDate

fun addAd() {
    val getOwners = JsonDatabase.getOwners()
    val ownerTvs = JsonDatabase.getVehiclesWithoutAds()
    var ownerInfo: Owner = Owner(0, "", "", "")
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
        ownerInfo = selectedOwner
        break
    }

    if (ownerTvs.isEmpty()) {
        println("Для владельца ${ownerInfo.name} нет транспортных средств без объявления.\n")
        return
    }

    while (true) {
        println("Выберите ТС по номеру:")

        for ((index, vehicle) in ownerTvs.withIndex()) {
            print("${index + 1}. ")
            displaySellVehicleType(vehicle)
            println("* ${vehicle.make} ${vehicle.model}")
            println("* Year: ${vehicle.year}")
            println("* Color: ${vehicle.color}")
            println("* Mileage: ${vehicle.mileage}")
            displayVehicleDetails(vehicle)
        }

        val userInput = readln().toIntOrNull()
        if (userInput !in 1..ownerTvs.size) {
            println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${ownerTvs.size}.")
            continue
        }
        if (userInput != null) {
            ownerVin = ownerTvs[userInput - 1].vin
        }
        break
    }

    while (true) {
        val userPriceInput = readDoubleInput("Введите цену")
        ownerPrice = userPriceInput
        break
    }

    val currentDate = LocalDate.now().toString()

    JsonDatabase.addAd(
        Ad(
            JsonDatabase.getMaxIndexAd(),
            ownerId = ownerInfo.id,
            vin = ownerVin,
            price = ownerPrice,
            date = currentDate,
        )
    )
    return
}

fun removeAd() {
    val ads = JsonDatabase.getAds()

    if (ads.isEmpty()) {
        println("Нет объявлений.")
        return
    }

    println("Выберите номер объявления:\n")

    for (ad in ads) {
        displayAd(ad)
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
                JsonDatabase.archiveAd(userInput.toInt(), reason, false)
                return
            }
        }
    }
}

fun editAd() {
    val ads = JsonDatabase.getAds()

    if (ads.isEmpty()) {
        println("Нет объявлений.")
        return
    }

    println("Выберите номер объявления:\n")

    for (ad in ads) {
        displayAd(ad)
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

                JsonDatabase.editAd(userInput.toInt(), newPrice)
                return
            }
        }
    }
}

fun displayAd(ad: Ad) {
    val vinTv = JsonDatabase.searchByVin(ad.vin)
    val ownerName = JsonDatabase.searchOwnerById(ad.ownerId)
    val priceHistoryString = ad.priceHistory
        ?.takeIf { it.size > 1 }
        ?.dropLast(1)
        ?.joinToString(", ") { it.toInt().toString() }
        ?: ""

    println("\nОбъявление №${ad.id} от ${ad.date}")
//    when (vinTv) {
//        is Car -> println("Продажа автомобиля:")
//        is Motorcycle -> println("Продажа мотоцикла:")
//        is СommercialTransport -> println("Продажа коммерческого транспорта:")
//    }
    displaySellVehicleType(vinTv)
    println("Seller: ${ownerName.name}, ${ownerName.phone}")
    println(" * ${vinTv.make} ${vinTv.model}")
    println(" * Year: ${vinTv.year}")
    println(" * Price: ${ad.price.toInt()}")
    if (priceHistoryString.isNotEmpty()) println(" * Price change history: $priceHistoryString")
    println(" * Color: ${vinTv.color}")
    println(" * Mileage: ${vinTv.mileage}")
    displayVehicleDetails(vinTv)
}

