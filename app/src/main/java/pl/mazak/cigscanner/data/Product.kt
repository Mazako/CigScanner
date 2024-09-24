package pl.mazak.cigscanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val code: String,
    val plnPrice: Float
)