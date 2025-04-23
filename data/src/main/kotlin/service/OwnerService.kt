package service

import JsonDatabase
import JsonDatabase.db
import entity.OwnerEntity

fun addOwner(owner: OwnerEntity) {
    if (db.owners.any { it.id == owner.id }) {
        println("Владелец с id ${owner.id} уже существует!")
        return
    }

    val newOwners = db.owners.toMutableList().apply { add(owner) }
    db = db.copy(owners = newOwners)
    JsonDatabase.saveDatabase()
}