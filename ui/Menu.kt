package org.example.ui

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