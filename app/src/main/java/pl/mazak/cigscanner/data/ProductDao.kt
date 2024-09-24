package pl.mazak.cigscanner.data;


import androidx.room.Dao;
import androidx.room.Query

import kotlinx.coroutines.flow.Flow;

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProduct(): Flow<List<Product>>

}
