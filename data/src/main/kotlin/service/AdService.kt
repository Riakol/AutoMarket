package service

import JsonDatabase.db
import JsonDatabase.saveDatabase
import entity.AdEntity

fun postAd(ad: AdEntity) {
    if (db.ads.any { it.id == ad.id }) {
        println("Объявление с id ${ad.id} уже существует!")
        return
    }

    val newAds = db.ads.toMutableList().apply { add(ad) }
    db = db.copy(ads = newAds)
    saveDatabase()
    println("\nОбъявление успешно добавлено.\n")
}

fun archiveAd(adId: Int, cancellationReason: String, isActive: Boolean) {
    val adIndex = db.ads.indexOfFirst { it.id == adId }
    if (adIndex == -1) {
        println("Объявление с id $adId не существует!")
        return
    }

    val ad = db.ads[adIndex]
    val updatedAd = ad.copy(
        reasonForCancellation = cancellationReason,
        isActive = isActive
    )

    val newAds = db.ads.toMutableList().apply { set(adIndex, updatedAd) }
    db = db.copy(ads = newAds)

    saveDatabase()
    println("Объявление с id $adId успешно удалено.\n")
}

fun configAd(adId: Int, newPrice: Double) {
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