import entity.VehicleEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File

object JsonDatabase {
    private val dbFile = File("database.json")

    private val vehicleSerializersModule = SerializersModule {
        polymorphic(VehicleEntity::class) {
            subclass(VehicleEntity.MotorcycleEntity::class)
            subclass(VehicleEntity.CarEntity::class)
            subclass(VehicleEntity.CommercialVehicleEntity::class)
        }
    }

    private val json = Json {
        serializersModule = vehicleSerializersModule
        prettyPrint = true
        classDiscriminator = "type"
        ignoreUnknownKeys = true
    }

    var db: Database = if (dbFile.exists()) {
        json.decodeFromString(dbFile.readText())
    } else {
        Database()
    }

    fun saveDatabase() {
        dbFile.writeText(json.encodeToString(db))
    }

    fun getNextIdOwner(): Int {
        return if (db.owners.isEmpty()) 1 else db.owners.maxOf { it.id } + 1
    }

    fun getNextIdAd(): Int {
        return if (db.ads.isEmpty()) 1 else db.ads.maxOf { it.id } + 1
    }
}