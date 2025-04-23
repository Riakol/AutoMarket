import entity.AdEntity
import entity.OwnerEntity
import entity.VehicleEntity
import kotlinx.serialization.Serializable

@Serializable
data class Database(
    val vehicles: List<VehicleEntity> = emptyList(),
    val owners: List<OwnerEntity> = emptyList(),
    val ads: List<AdEntity> = emptyList(),
)