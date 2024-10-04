package pl.mazak.cigscanner.data.db.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    val id: Int,
    val euro: Double,
    val czechCrown: Double
)