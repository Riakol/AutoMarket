package utils

import entity.VehicleEntity
import kotlin.enums.enumEntries

fun displayMenu(){
    println("Выберите действие:")
    println("1. Добавить данные владельца")
    println("2. Добавить транспортное средство")
    println("3. Добавить объявление")
    println("4. Снять объявление")
    println("5. Редактировать объявление")
    println("6. Поиск по объявлениям\n")
}

fun displayVehicleTypeMenu() {
    println("Выберите тип ТС:")
    println("1. Автомобиль")
    println("2. Мотоцикл")
    println("3. Коммерческий транспорт")
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

fun displayVehicleDetails(vehicle: VehicleEntity) {
    when (vehicle) {
        is VehicleEntity.CarEntity -> println("* Car Type: ${vehicle.carType}\n")
        is VehicleEntity.MotorcycleEntity -> println("* Motorcycle Type: ${vehicle.motoType}\n")
        is VehicleEntity.CommercialVehicleEntity -> println("* Load Capacity: ${vehicle.loadCapacity.toInt()}\n")
    }
}

fun displaySellVehicleType(vehicle: VehicleEntity){
    when(vehicle) {
        is VehicleEntity.CarEntity -> println("Продажа автомобиля:")
        is VehicleEntity.MotorcycleEntity -> println("Продажа мотоцикла:")
        is VehicleEntity.CommercialVehicleEntity -> println("Продажа коммерческого транспорта:")
    }
}

inline fun <reified T : Enum<T>> displayVehicleType() {
    val vehicleTypes = enumEntries<T>()

    println("Выберите тип транспорта по номеру:")
    vehicleTypes.forEachIndexed { index, type ->
        println("${index + 1}. $type")
    }
}