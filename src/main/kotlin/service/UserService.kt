//package org.example.service
//
//import JsonDatabase
//import JsonDatabase.db
//import models.Owner
//import org.example.consoleutils.readStringInput
//
//fun addOwner() {
//    val name = readStringInput("Введите имя владельца")
//    val phone = readStringInput("Введите номер телефона владельца")
//    val email = readStringInput("Введите электронную почту владельца")
//
//    addOwner(
//        Owner(
//            id = JsonDatabase.getNextIdOwner(),
//            name = name,
//            phone = phone,
//            email = email
//        )
//    )
//    println("\n$name успешно добавлен.\n")
//}
//
//fun addOwner(owner: Owner) {
//    if (db.owners.any { it.id == owner.id }) {
//        println("Владелец с id ${owner.id} уже существует!")
//        return
//    }
//
//    val newOwners = db.owners.toMutableList().apply { add(owner) }
//    db = db.copy(owners = newOwners)
//    JsonDatabase.saveDatabase()
//}