package pl.mazak.cigscanner.data.db.product

import kotlinx.coroutines.flow.Flow


interface ProductsRepository {
    fun getAllProductsStream(): Flow<List<Product>>

    fun getProductById(id: Int): Flow<Product>

    suspend fun addProduct(product: Product)

    suspend fun removeProductById(id: Int)

    suspend fun updateProduct(product: Product)
}