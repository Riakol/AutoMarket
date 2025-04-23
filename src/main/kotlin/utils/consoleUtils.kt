package org.example.consoleutils

fun readStringInput(prompt: String): String {
    print("$prompt: ")
    var input: String?
    do {
        input = readlnOrNull()?.trim()
        if (input.isNullOrBlank()) {
            println("Некорректный ввод. Пожалуйста, введите число.")
            print("$prompt: ")
        }
    } while (input.isNullOrBlank())
    return input
}

fun readIntInput(prompt: String): Int {
    var value: Int? = null
    while (value == null) {
        print("$prompt: ")
        value = readlnOrNull()?.toInt()
        if (value == null) {
            println("Некорректный ввод. Пожалуйста, введите число.")
        }
    }
    return value
}

fun readDoubleInput(prompt: String): Double {
    var value: Double? = null
    while (value == null) {
        print("$prompt: ")
        value = readlnOrNull()?.toDouble()
        if (value == null) {
            println("Некорректный ввод. Пожалуйста, введите число.")
        }
    }
    return value
}
