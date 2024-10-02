package pl.mazak.cigscanner.data;


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProduct(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getProduct(id: Int): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Query("DELETE FROM products WHERE id=:id")
    suspend fun removeProduct(id: Int)

    @Update
    suspend fun updateProduct(product: Product)
}
