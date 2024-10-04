package pl.mazak.cigscanner.data.db.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency WHERE id=1")
    fun getCurrency(): Flow<Currency?>

    @Update
    suspend fun update(currency: Currency)

    @Insert
    suspend fun insert(currency: Currency)
}