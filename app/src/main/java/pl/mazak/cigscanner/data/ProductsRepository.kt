package pl.mazak.cigscanner.data

import kotlinx.coroutines.flow.Flow


interface ProductsRepository {
    fun getAllProductsStream(): Flow<List<Product>>

    suspend fun addProduct(product: Product)

    suspend fun removeProductById(id: Int)
}