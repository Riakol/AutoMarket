package org.example.service

import JsonDatabase
import JsonDatabase.db
import JsonDatabase.saveDatabase
import org.example.consoleutils.readDoubleInput

//fun addAd() {
//    val getOwners = JsonDatabase.getOwners()
//    val showVehicles = JsonDatabase.getVehiclesWithoutAds()
//    var ownerInfo: Owner = Owner(0, "", "", "")
//    var ownerVin: String = ""
//    var ownerPrice: Double = 0.0
//
//    while (true) {
//        println("Выберите владельца по id:\n")
//
//        for (owner in getOwners) println("${owner.id}. ${owner.name}")
//        val ownerInput = readln().toIntOrNull()
//
//        val selectedOwner = getOwners.find { ownerInput != null && it.id == ownerInput }
//        if (selectedOwner == null) {
//            println("Владелец с id $ownerInput не найден. Попробуйте снова.\n")
//            continue
//        }
//        ownerInfo = selectedOwner
//        break
//    }
//
//    if (showVehicles.isEmpty()) {
//        println("Для владельца ${ownerInfo.name} нет транспортных средств без объявления.\n")
//        return
//    }
//
//    while (true) {
//        println("Выберите ТС по номеру:")
//
//        for ((index, vehicle) in showVehicles.withIndex()) {
//            print("${index + 1}. ")
//            displaySellVehicleType(vehicle)
//            println("* ${vehicle.make} ${vehicle.model}")
//            println("* Year: ${vehicle.year}")
//            println("* Color: ${vehicle.color}")
//            println("* Mileage: ${vehicle.mileage}")
//            displayVehicleDetails(vehicle)
//        }
//
//        val userInput = readln().toIntOrNull()
//        if (userInput !in 1..showVehicles.size) {
//            println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${showVehicles.size}.")
//            continue
//        }
//        if (userInput != null) {
//            ownerVin = showVehicles[userInput - 1].vin
//        }
//        break
//    }
//
//    while (true) {
//        val userPriceInput = readDoubleInput("Введите цену")
//        ownerPrice = userPriceInput
//        break
//    }
//
//    val currentDate = LocalDate.now().toString()
//
//    addAd(
//        Ad(
//            JsonDatabase.getNextIdAd(),
//            ownerId = ownerInfo.id,
//            vin = ownerVin,
//            price = ownerPrice,
//            date = currentDate,
//        )
//    )
//    return
//}

//fun removeAd() {
//    val ads = JsonDatabase.getAds()
//
//    if (ads.isEmpty()) {
//        println("Нет объявлений.")
//        return
//    }
//
//    println("Выберите номер объявления:\n")
//
//    for (ad in ads) {
//        displayAd(ad)
//    }
//
//    var userInput = readln()
//
//    while (true) {
//        when {
//            db.ads.none { it.id == userInput.toInt() && it.isActive } -> {
//                println("Объявления с id $userInput не существует. Пожалуйста, выберите корректный id")
//                userInput = readln()
//                if (userInput.all { it.isDigit() }) continue else break
//            }
//            else -> {
//                println("Укажите причину снятия объявления:")
//                val reason = readln()
//                archiveAd(userInput.toInt(), reason, false)
//                return
//            }
//        }
//    }
//}

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

                editAd(userInput.toInt(), newPrice)
                return
            }
        }
    }
}

//fun displayAd(ad: Ad) {
//    val vinTv = searchByVin(ad.vin)
//    val ownerName = searchOwnerById(ad.ownerId)
//    val priceHistoryString = ad.priceHistory
//        ?.takeIf { it.size > 1 }
//        ?.dropLast(1)
//        ?.joinToString(", ") { it.toInt().toString() }
//        ?: ""
//
//    println("Объявление №${ad.id} от ${ad.date}")
//    displaySellVehicleType(vinTv)
//    println("Seller: ${ownerName.name}, ${ownerName.phone}")
//    println("* ${vinTv.make} ${vinTv.model}")
//    println("* Year: ${vinTv.year}")
//    println("* Price: ${ad.price.toInt()}")
//    if (priceHistoryString.isNotEmpty()) println("* Price change history: $priceHistoryString")
//    println("* Color: ${vinTv.color}")
//    println("* Mileage: ${vinTv.mileage}")
//    displayVehicleDetails(vinTv)
//}

//fun addAd(ad: Ad) {
//    if (db.ads.any { it.id == ad.id }) {
//        println("Объявление с id ${ad.id} уже существует!")
//        return
//    }
//
//    val newAds = db.ads.toMutableList().apply { add(ad) }
//    db = db.copy(ads = newAds)
//    saveDatabase()
//    println("\nОбъявление успешно добавлено.\n")
//}

fun editAd(adId: Int, newPrice: Double) {
    val adIndex = db.ads.indexOfFirst { it.id == adId }
    if (adIndex == -1) {
        println("Объявление с ID $adId не найдено.")
        return
    }

    val ad = db.ads[adIndex]

    val updatedAd = ad.copy(
        price = newPrice,
        priceHistory = (ad.priceHistory ?: emptyList()) + newPrice
    )

    val newAds = db.ads.toMutableList().apply { set(adIndex, updatedAd) }
    db = db.copy(ads = newAds)

    saveDatabase()
    println("Объявление №$adId успешно изменено. Новая цена: ${newPrice.toInt()}\n")
}

//fun archiveAd(adId: Int, cancellationReason: String, isActive: Boolean) {
//    val adIndex = db.ads.indexOfFirst { it.id == adId }
//    if (adIndex == -1) {
//        println("Объявление с id $adId не существует!")
//        return
//    }
//
//    val ad = db.ads[adIndex]
//    val updatedAd = ad.copy(
//        reasonForCancellation = cancellationReason,
//        isActive = isActive
//    )
//
//    val newAds = db.ads.toMutableList().apply { set(adIndex, updatedAd) }
//    db = db.copy(ads = newAds)
//
//    saveDatabase()
//    println("Объявление с id $adId успешно архивировано.\n")
//}