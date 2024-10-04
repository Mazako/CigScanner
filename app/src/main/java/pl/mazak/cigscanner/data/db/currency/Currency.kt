package pl.mazak.cigscanner.data.db.currency

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    val id: Int,
    val euro: BigDecimal,
    val czechCrown: BigDecimal
)