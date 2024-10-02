package pl.mazak.cigscanner.data

import kotlinx.coroutines.flow.Flow

class OfflineProductsRepository(private val productDao: ProductDao) : ProductsRepository {
    override fun getAllProductsStream(): Flow<List<Product>> = productDao.getAllProduct()
    override suspend fun addProduct(product: Product) = productDao.addProduct(product)
    override suspend fun removeProductById(id: Int) = productDao.removeProduct(id)
}