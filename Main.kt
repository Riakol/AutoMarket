package org.example

import org.example.database.JsonDatabase
import org.example.database.JsonDatabase.db
import org.example.models.*
import java.time.LocalDate

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val running = true

    while (running) {
        displayMenu()
        val choiceMain = readln().toIntOrNull()

        if (choiceMain == null || choiceMain !in 1..6) {
            println("Некорректный выбор. Пожалуйста, выберите число от 1 до 6.\n")
            continue
        }

        while (true) {
            when (choiceMain) {
                1 -> {
                    println("Введите имя владельца:")
                    val name = readln()

                    println("Введите номер телефона владельца:")
                    val phone = readln()

                    println("Введите электронную почту владельца:")
                    val email = readln()

                    JsonDatabase.addOwner(
                        Owner(
                            id = JsonDatabase.getMaxIndexOwner(),
                            name = name,
                            phone = phone,
                            email = email
                        )
                    )
                    println("$name успешно добавлен.")
                    break
                }
                2 -> {
                    displayVehicleTypeMenu()
                    println("Чтобы вернуться назад введите — q\n")

                    val choiceTV = readln().toInt()

                    if (choiceTV !in 1..3) {
                        println("\nНекорректный выбор. Пожалуйста, выберите число от 1 до 3.")
                        continue
                    }

                    when (choiceTV) {
                        1 -> {
                            println("\nВведите VIN автомобиля:")
                            val vin = readln()
                            println("Введите марку автомобиля:")
                            val make = readln()
                            println("Введите модель автомобиля:")
                            val model = readln()
                            println("Введите год выпуска автомобиля:")
                            val year = readln().toInt()
                            println("Введите цвет автомобиля:")
                            val color = readln()
                            println("Введите пробег автомобиля:")
                            val mileage = readln().toInt()

                            while (true) {
                                displayCarType()
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
                                println("Автомобиль успешно добавлен.\n")
                                break
                            }
                        }
                        2 -> {
                            println("Введите VIN мотоцикла:")
                            val vin = readln()
                            println("Введите марку мотоцикла:")
                            val make = readln()
                            println("Введите модель мотоцикла:")
                            val model = readln()
                            println("Введите год выпуска мотоцикла:")
                            val year = readln().toInt()
                            println("Введите цвет мотоцикла:")
                            val color = readln()
                            println("Введите пробег мотоцикла:")
                            val mileage = readln().toInt()

                            while (true) {
                                println("Выберите тип мотоцикла:")
                                for ((index, moto) in MotoType.entries.withIndex()) {
                                    println("${index + 1}. $moto")
                                }

                                val motoType = readln().toInt()

                                if (motoType !in 1..MotoType.entries.size) {
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
                                println("Мотоцикл успешно добавлен.\n")
                                break
                            }
                        }
                        3 -> {
                            println("Введите VIN коммерческого транспорта:")
                            val vin = readln()
                            println("Введите марку коммерческого транспорта:")
                            val make = readln()
                            println("Введите модель коммерческого транспорта:")
                            val model = readln()
                            println("Введите год выпуска коммерческого транспорта:")
                            val year = readln().toInt()
                            println("Введите цвет коммерческого транспорта:")
                            val color = readln()
                            println("Введите пробег коммерческого транспорта:")
                            val mileage = readln().toInt()
                            println("Введите грузоподьемность коммерческого транспорта:")
                            val capacity = readln().toDouble()

                            JsonDatabase.addVehicle(
                                СommercialTransport(
                                    vin = vin,
                                    make = make,
                                    model = model,
                                    year = year,
                                    color = color,
                                    mileage = mileage,
                                    loadCapacity = capacity
                                )
                            )
                            println("Коммерческий транспорт успешно добавлен.\n")
                        }
                    }
                }
                3 -> {
                    val getOwners = JsonDatabase.getOwners()
                    val ownerTvs = JsonDatabase.getVehiclesWithoutAds()
                    var ownerDetails: Owner = Owner(0, "", "", "")
                    var ownerVin: String = ""
                    var ownerPrice: Double = 0.0

                    while (true) {
                        println("Выберите владельца по id:\nЧтобы вернуться назад введите — q\n")

                        for (owner in getOwners) println("${owner.id}. ${owner.name}")
                        val ownerInput = readln()

                        when {
                            ownerInput.equals("q", ignoreCase = true) -> break
                            ownerInput.all { it.isDigit() } -> {

                                val selectedOwner = getOwners.find { it.id == ownerInput.toInt() }
                                if (selectedOwner == null) {
                                    println("Владелец с id $ownerInput не найден. Попробуйте снова.\n")
                                    continue
                                }
                                ownerDetails = selectedOwner
                                break
                            }
                        }
                    }

                    if (ownerTvs.isEmpty()) {
                        println("Для владельца ${ownerDetails.name} нет транспортных средств без объявления.\n")
                        break
                    }

                    while (true) {
                        println("Выберите ТС по номеру:")

                        for ((index, vehicle) in ownerTvs.withIndex()) {
                            print("${index + 1}.\n * ${vehicle.make} ${vehicle.model}\n * Year: ${vehicle.year}\n * Color: ${vehicle.color}\n * Mileage: ${vehicle.mileage}\n")
                            displayVehicleDetails(vehicle)
                            //if (vehicle is Car) println("* Car Type: ${vehicle.carType}\n")
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
                        println("Введите цену: ")
                        val userPriceInput = readlnOrNull()?.toDoubleOrNull()

                        if (userPriceInput == null) {
                            println("Некорректный ввод. Пожалуйста, введите число.")
                            continue
                        }

                        ownerPrice = userPriceInput
                        break
                    }

                    val currentDate = LocalDate.now().toString()

                    JsonDatabase.addAd(
                        Ad(
                            JsonDatabase.getMaxIndexAd(),
                            ownerId = ownerDetails.id,
                            vin = ownerVin,
                            price = ownerPrice,
                            date = currentDate,
                        )
                    )
                }
                4 -> {
                    val ads = JsonDatabase.getAds()
                    if (ads.isEmpty()) {
                        println("Нет объявлений.")
                        break
                    }

                    println("Выберите номер объявления:\n" +
                            "Чтобы вернуться назад введите — q\n")

                    for (ad in ads) {
                        displayAd(ad)
                    }

                    var userInput = readln()

                    when {
                        userInput.equals("q", ignoreCase = true) -> break
                        userInput.all { it.isDigit() } -> {
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
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                5 -> {
                    val ads = JsonDatabase.getAds()
                    if (ads.isEmpty()) {
                        println("Нет объявлений.")
                        break
                    }

                    println("Выберите номер объявления:\n" +
                            "Чтобы вернуться назад введите — q\n")

                    for (ad in ads) {
                        displayAd(ad)
                    }

                    val userInput = readln()

                    when {
                        userInput.equals("q", ignoreCase = true) -> break
                        userInput.all { it.isDigit() } -> {
                            while (true) {
                                when {
                                    db.ads.none { it.id == userInput.toInt() && it.isActive } -> {
                                        println("Объявления с id $userInput не существует. Пожалуйста, выберите корректный id")
                                        if (userInput.all { it.isDigit() }) continue else break
                                    }
                                    else -> {
                                        println("Укажите новую цену:")
                                        val newPrice = readln().toDoubleOrNull()

                                        if (newPrice == null) {
                                            println("Некорректный ввод. Пожалуйста, введите число.")
                                            continue
                                        }
                                        JsonDatabase.editAd(userInput.toInt(), newPrice)
                                        JsonDatabase.savePriceHistory(userInput.toInt(), listOf(newPrice))
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                6 -> {
                    displayVehicleTypeMenu()
                    println("4. Общий поиск")
                    println("5. Вернуться назад")

                    when (val userInput = readln().toInt()) {
                        1 -> {
                            displaySearchCriteria(userInput)

                            while (true) {
                                when (val userChoice = readln().toInt()) {
                                    1 -> {
                                        println("Введите стоимость ТС:")
                                        val userPrice = readln().toDouble()
                                        println("Введите пробег:")
                                        val userMileage = readln().toInt()
                                        val result = JsonDatabase.searchVehicleByPriceAndMileage(userPrice, userMileage)

                                        if (result.isEmpty()) {
                                            println("Объявлений нет")
                                            break
                                        }

                                        for (tv in result) {
                                            displayAd(tv)
                                        }
                                        break
                                    }
                                    2 -> {
                                        println("Введите цвет:")
                                        val userColor = readln()
                                        val vehicleColor = JsonDatabase.searchVehicleByColor(userColor)

                                        if (vehicleColor.isEmpty()) {
                                            println("Объявлений нет")
                                            break
                                        }

                                        for (tv in vehicleColor) {
                                            displayAd(tv)
                                        }
                                        break
                                    }
                                    3 -> {
                                        displayCarType()
                                        val userCarType = readln().toInt()
                                        while (true) {
                                            if (userCarType !in 1..CarType.entries.size) {
                                                println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${CarType.entries.size}.")
                                                continue
                                            }

                                            val vehicleType =
                                                JsonDatabase.searchVehicleByType(CarType.entries[userCarType - 1].name)

                                            if (vehicleType.isEmpty()) println("Объявлений нет")

                                            for (tv in vehicleType) {
                                                displayAd(tv)
                                            }
                                            break
                                        }
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
                                        println("Введите стоимость ТС:")
                                        val userPrice = readln().toDouble()
                                        println("Введите пробег:")
                                        val userMileage = readln().toInt()
                                        val result = JsonDatabase.searchVehicleByPriceAndMileage(userPrice, userMileage)

                                        if (result.isEmpty()) {
                                            println("Объявлений нет")
                                            break
                                        }

                                        for (tv in result) {
                                            displayAd(tv)
                                        }
                                        break
                                    }
                                    2 -> {
                                        println("Введите цвет:")
                                        val userColor = readln()
                                        val vehicleColor = JsonDatabase.searchVehicleByColor(userColor)

                                        if (vehicleColor.isEmpty()) {
                                            println("Объявлений нет")
                                            break
                                        }

                                        for (tv in vehicleColor) {
                                            displayAd(tv)
                                        }
                                        break
                                    }
                                    3 -> {
                                        displayCarType()
                                        val userCarType = readln().toInt()
                                        while (true) {
                                            if (userCarType !in 1..CarType.entries.size) {
                                                println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${CarType.entries.size}.")
                                                continue
                                            }

                                            val vehicleType =
                                                JsonDatabase.searchVehicleByType(CarType.entries[userCarType - 1].name)

                                            if (vehicleType.isEmpty()) println("Объявлений нет")

                                            for (tv in vehicleType) {
                                                displayAd(tv)
                                            }
                                            break
                                        }
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
                                        println("Введите стоимость ТС:")
                                        val userPrice = readln().toDouble()
                                        println("Введите пробег:")
                                        val userMileage = readln().toInt()
                                        val result = JsonDatabase.searchVehicleByPriceAndMileage(userPrice, userMileage)

                                        if (result.isEmpty()) {
                                            println("Объявлений нет")
                                            break
                                        }

                                        for (tv in result) {
                                            displayAd(tv)
                                        }
                                        break
                                    }
                                    2 -> {
                                        println("Введите цвет:")
                                        val userColor = readln()
                                        val vehicleColor = JsonDatabase.searchVehicleByColor(userColor)

                                        if (vehicleColor.isEmpty()) {
                                            println("Объявлений нет")
                                            break
                                        }

                                        for (tv in vehicleColor) {
                                            displayAd(tv)
                                        }
                                        break
                                    }
                                    3 -> {
                                        println("\nВведите грузоподъемность:")
                                        val userLoadCapacity = readln().toDoubleOrNull()
                                        while (true) {
                                            if (userLoadCapacity == null) {
                                                println("Некорректный ввод. Пожалуйста, введите число.")
                                                continue
                                            }

                                            val vehicleType =
                                                JsonDatabase.searchVehicleByCapacity(userLoadCapacity)

                                            if (vehicleType.isEmpty()) println("Объявлений нет")

                                            for (tv in vehicleType) {
                                                displayAd(tv)
                                            }
                                            break
                                        }
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
                            break
                        }
                        5 -> {
                            break
                        }
                    }
                }
            }
        }
    }
}


fun displayMenu(){
    println("Выберите действие:")
    println("1. Добавить данные владельца")
    println("2. Добавить транспортное средство")
    println("3. Добавить объявление")
    println("4. Снять объявление")
    println("5. Редактировать объявление")
    println("6. Поиск по объявлениям\n")
}

fun displaySearchCriteria(userInput: Int) {
    println("Выберите критерии поиска по номеру:")
    println("1. С определенной стоимостью и пробегом")
    println("2. С определенным цветом")
    when(userInput) {
        in 1..2 -> println("3. С определенным типом")
        3 -> println("3. С определенным тоннажем")
    }
    println("4. Назад")
}

fun displayVehicleTypeMenu() {
    println("Выберите тип ТС:")
    println("1. Автомобиль")
    println("2. Мотоцикл")
    println("3. Коммерческий транспорт")
}

fun displayAd(ad: Ad) {
    val vinTv = JsonDatabase.searchByVin(ad.vin)
    val ownerName = JsonDatabase.searchOwnerById(ad.ownerId)
    val priceHistoryString = ad.priceHistory
        ?.takeIf { it.size > 1 }
        ?.dropLast(1)
        ?.joinToString(", ") { it.toInt().toString() }
        ?: ""

    println("Объявление №${ad.id} от ${ad.date}")
    when (vinTv) {
        is Car -> println("Продажа автомобиля:")
        is Motorcycle -> println("Продажа мотоцикла:")
        is СommercialTransport -> println("Продажа коммерческого транспорта:")
    }
    println("Seller: ${ownerName.name}, ${ownerName.phone}")
    println(" * ${vinTv.make} ${vinTv.model}")
    println(" * Year: ${vinTv.year}")
    println(" * Price: ${ad.price.toInt()}")
    if (priceHistoryString.isNotEmpty()) println(" * Price change history: $priceHistoryString")
    println(" * Color: ${vinTv.color}")
    println(" * Mileage: ${vinTv.mileage}")
    displayVehicleDetails(vinTv)
}

fun displayCarType() {
    println("Выберите тип автомобиля по номеру:")
    for (car in CarType.entries.indices) {
        println("${car + 1}. ${CarType.entries[car]}")
    }
}

fun displayVehicleDetails(vehicle: Vehicle) {
    when (vehicle) {
        is Car -> println(" * Car Type: ${vehicle.carType}\n")
        is Motorcycle -> println(" * Motorcycle Type: ${vehicle.motoType}\n")
        is СommercialTransport -> println(" * Load Capacity: ${vehicle.loadCapacity.toInt()}\n")
    }
}
