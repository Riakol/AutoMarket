package org.example.service

import org.example.consoleutils.readStringInput
import org.example.database.JsonDatabase
import org.example.models.Owner

fun addOwner() {
    val name = readStringInput("Введите имя владельца")
    val phone = readStringInput("Введите номер телефона владельца")
    val email = readStringInput("Введите электронную почту владельца")

    JsonDatabase.addOwner(
        Owner(
            id = JsonDatabase.getMaxIndexOwner(),
            name = name,
            phone = phone,
            email = email
        )
    )
    println("\n$name успешно добавлен.\n")
}