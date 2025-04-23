package org.example.service

//fun addVehicle() {
//    displayVehicleTypeMenu()
//
//    while (true) {
//        val choiceTV = readln().toInt()
//
//        if (choiceTV !in 1..3) {
//            println("\nНекорректный выбор. Пожалуйста, выберите число от 1 до 3.")
//            continue
//        }
//
//        val vin = readStringInput("Введите VIN")
//        val make = readStringInput("Введите марку")
//        val model = readStringInput("Введите модель")
//        val year = readIntInput("Введите год выпуска")
//        val color = readStringInput("Введите цвет")
//        val mileage = readIntInput("Введите пробег")
//
//        when (choiceTV) {
//            1 -> {
//                while (true) {
//                    displayVehicleType<CarType>()
//                    val carType = readln().toInt()
//
//                    if (carType !in 1..CarType.entries.size) {
//                        println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${CarType.entries.size}.")
//                        continue
//                    }
//                    addVehicle(
//                        Vehicle.Car(
//                            vin = vin,
//                            make = make,
//                            model = model,
//                            year = year,
//                            color = color,
//                            mileage = mileage,
//                            carType = CarType.entries[carType - 1]
//                        )
//                    )
//                    println("\nАвтомобиль успешно добавлен.\n")
//                    return
//                }
//            }
//            2 -> {
//                while (true) {
//                    displayVehicleType<MotoType>()
//                    val motoType = readln().toInt()
//
//                    if (motoType !in 1..CarType.entries.size) {
//                        println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${MotoType.entries.size}.")
//                        continue
//                    }
//                    addVehicle(
//                        Vehicle.Motorcycle(
//                            vin = vin,
//                            make = make,
//                            model = model,
//                            year = year,
//                            color = color,
//                            mileage = mileage,
//                            motoType = MotoType.entries[motoType - 1]
//                        )
//                    )
//                    println("\nМотоцикл успешно добавлен.\n")
//                    return
//                }
//            }
//            3 -> {
//                while (true) {
//                    val loadCapacity = readIntInput("Введите грузоподъемность")
//                    addVehicle(
//                        Vehicle.CommercialTransport(
//                            vin = vin,
//                            make = make,
//                            model = model,
//                            year = year,
//                            color = color,
//                            mileage = mileage,
//                            loadCapacity = loadCapacity.toDouble()
//                        )
//                    )
//                    println("\nКоммерческий транспорт успешно добавлен.\n")
//                    return
//                }
//            }
//        }
//    }
//}

//fun saveVehicleTypeSelection(userInput: Int) {
//    while (true) {
//        when (readln().toInt()) {
//            1 -> {
//                findVehiclesByCostAndMileage(userInput)
//                break
//            }
//            2 -> {
//                findVehiclesByColor(userInput)
//                break
//            }
//            3 -> {
//                when(userInput) {
//                    1 -> {
//                        displayVehicleType<CarType>()
//                        findVehiclesByType<CarType>()
//                        break
//                    }
//                    2 -> {
//                        displayVehicleType<MotoType>()
//                        findVehiclesByType<MotoType>()
//                        break
//                    }
//                    3 -> {
//                        val userLoadCapacity = readDoubleInput("\nВведите грузоподъемность")
//                        val vehicleType =
//                            searchVehicleByCapacity(userLoadCapacity)
//
//                        if (vehicleType.isEmpty()) println("Объявлений нет")
//
//                        for (tv in vehicleType) {
//                            displayAd(tv)
//                        }
//                        break
//                    }
//                }
//            }
//            4 -> break
//        }
//    }
//}

//fun searchListingsMenu() {
//    displayVehicleTypeMenu()
//    println("4. Общий поиск")
//    println("5. Вернуться назад")
//
//    var userInput: Int? = null
//    do {
//        userInput = readln().toIntOrNull() ?: continue
//        if (userInput !in 1..5) {
//            println("Некорректный выбор. Пожалуйста, выберите число от 1 до 5.")
//        }
//    } while (userInput !in 1..5)
//
//    when (userInput) {
//        1, 2, 3 -> {
//            displaySearchCriteria(userInput)
//            saveVehicleTypeSelection(userInput)
//        }
//        4 -> {
//            val ads = JsonDatabase.getAds()
//            for (ad in ads) {
//                displayAd(ad)
//            }
//        }
//        5 -> {
//            return
//        }
//    }
//}

//fun findVehiclesByCostAndMileage(numVehicleType: Int) {
//    val userPrice = readDoubleInput("Введите стоимость ТС")
//    val userMileage = readIntInput("Введите пробег")
//    val result = searchVehicleByPriceAndMileage(userPrice, userMileage, numVehicleType)
//
//    if (result.isEmpty()) {
//        println("Объявлений нет\n")
//        return
//    }
//
//    for (tv in result) {
//        displayAd(tv)
//    }
//}
//
//fun findVehiclesByColor(num: Int) {
//    val userColor = readStringInput("Введите цвет\n")
//    val vehicleColor = searchVehicleByColor(userColor, num)
//
//    if (vehicleColor.isEmpty()) {
//        println("Объявлений нет\n")
//        return
//    }
//
//    for (tv in vehicleColor) {
//        displayAd(tv)
//    }
//}

//inline fun <reified T : Enum<T>> findVehiclesByType() {
//    val userVehicleType = readln().toInt()
//    val vehicleTypes = enumValues<T>()
//
//    while (true) {
//        if (userVehicleType !in 1..vehicleTypes.size) {
//            println("Некорректный выбор. Пожалуйста, выберите число от 1 до ${vehicleTypes.size}.")
//            continue
//        }
//
//        val vehicleType = searchVehicleByType(vehicleTypes[userVehicleType - 1].name)
//
//        if (vehicleType.isEmpty()) {
//            println("Объявлений нет")
//            break
//        }
//
//        vehicleType.forEach { displayAd(it) }
//        break
//    }
//}

//inline fun <reified T : Enum<T>> displayVehicleType() {
//    val vehicleTypes = enumEntries<T>()
//
//    println("Выберите тип транспорта по номеру:")
//    vehicleTypes.forEachIndexed { index, type ->
//        println("${index + 1}. $type")
//    }
//}


//fun displayVehicleDetails(vehicle: Vehicle) {
//    when (vehicle) {
//        is Vehicle.Car -> println("* Car Type: ${vehicle.carType}\n")
//        is Vehicle.Motorcycle -> println("* Motorcycle Type: ${vehicle.motoType}\n")
//        is Vehicle.CommercialTransport -> println("* Load Capacity: ${vehicle.loadCapacity.toInt()}\n")
//    }
//}
//
//fun displaySellVehicleType(vehicle: Vehicle){
//    when(vehicle) {
//        is Vehicle.Car -> println("Продажа автомобиля:")
//        is Vehicle.Motorcycle -> println("Продажа мотоцикла:")
//        is Vehicle.CommercialTransport -> println("Продажа коммерческого транспорта:")
//    }
//}

//fun searchOwnerById(id: Int): Owner {
//    return db.owners.filter { it.id == id}[0]
//}

//fun searchByVin(vin: String): Vehicle {
//    return db.vehicles.filter {it.vin == vin}[0]
//}

//fun addVehicle(vehicle: Vehicle) {
//    if (db.vehicles.any { it.vin == vehicle.vin }) {
//        println("Транспортное средство с vin ${vehicle.vin} уже существует!")
//        return
//    }
//
//    val newVehicles = db.vehicles.toMutableList().apply { add(vehicle) }
//    db = db.copy(vehicles = newVehicles)
//    saveDatabase()
//}

//fun searchVehicleByColor(color: String, numVehicleType: Int): List<Ad> {
//    return db.ads.filter { ad ->
//        db.vehicles.any { vehicle ->
//            vehicle.vin == ad.vin &&
//                    vehicle.color.contains(color, ignoreCase = true) &&
//                    when (numVehicleType) {
//                        1 -> vehicle is Vehicle.Car
//                        2 -> vehicle is Vehicle.Motorcycle
//                        else -> vehicle is Vehicle.CommercialTransport
//                    }
//        }
//    }
//}

//fun searchVehicleByPriceAndMileage(price: Double, mileage: Int, numVehicleType: Int): List<Ad> {
//    return db.ads.filter { ad ->
//        ad.price == price && db.vehicles.any { vehicle ->
//            vehicle.vin == ad.vin &&
//                    vehicle.mileage == mileage &&
//                    when(numVehicleType) {
//                        1 -> vehicle is Vehicle.Car
//                        2 -> vehicle is Vehicle.Motorcycle
//                        else -> vehicle is Vehicle.CommercialTransport
//                    }
//        }
//    }
//}

//fun searchVehicleByType(tvType: String): List<Ad> {
//    return db.ads.filter { ad ->
//        db.vehicles.find { tv -> tv.vin == ad.vin }?.let { vehicle ->
//            when (vehicle) {
//                is Vehicle.Car -> vehicle.carType.name == tvType
//                is Vehicle.Motorcycle -> vehicle.motoType.name == tvType
//                else -> false
//            }
//        } ?: false
//    }
//}

//fun searchVehicleByCapacity(tvCapacity: Double): List<Ad> {
//    return db.ads.filter { ad ->
//        db.vehicles.find { tv -> tv.vin == ad.vin }?.let { vehicle ->
//            when (vehicle) {
//                is Vehicle.CommercialTransport -> vehicle.loadCapacity == tvCapacity
//                else -> false
//            }
//        } ?: false
//    }
//}