package service

import JsonDatabase.db
import JsonDatabase.saveDatabase
import entity.AdEntity

fun addAd(ad: AdEntity) {
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

fun postAd(adEntity: AdEntity) {
    if (db.ads.any { it.id == adEntity.id }) {
        println("Объявление с id ${adEntity.id} уже существует!")
        return
    }

    val newAds = db.ads.toMutableList().apply { add(adEntity) }
    db = db.copy(ads = newAds)
    saveDatabase()
    println("\nОбъявление успешно добавлено.\n")
}

private fun removeAd(adId: Int, cancellationReason: String, isActive: Boolean) {
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
    println("Объявление с id $adId успешно архивировано.\n")
}