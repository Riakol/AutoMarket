package org.example

import org.example.service.*
import org.example.ui.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    while (true) {
        displayMenu()
        val choiceMain = readln().toIntOrNull()

        if (choiceMain == null || choiceMain !in 1..6) {
            println("Некорректный выбор. Пожалуйста, выберите число от 1 до 6.\n")
            continue
        }

        while (true) {
            when (choiceMain) {
                1 -> { addOwner(); break }
                2 -> { addVehicle(); break }
                3 -> { addAd(); break }
                4 -> { removeAd(); break }
                5 -> { editAd(); break }
                6 -> { searchListingsMenu(); break }
            }
        }
    }
}
