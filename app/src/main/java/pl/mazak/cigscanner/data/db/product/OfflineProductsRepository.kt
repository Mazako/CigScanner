package pl.mazak.cigscanner.data.db.product

import kotlinx.coroutines.flow.Flow

class OfflineProductsRepository(private val productDao: ProductDao) : ProductsRepository {
    override fun getAllProductsStream(): Flow<List<Product>> = productDao.getAllProduct()
    override fun getProductById(id: Int): Flow<Product> = productDao.getProduct(id)
    override fun getProductByCodeStream(code: String): Flow<Product?> = productDao.getProductByCode(code)
    override suspend fun addProduct(product: Product) = productDao.addProduct(product)
    override suspend fun removeProductById(id: Int) = productDao.removeProduct(id)
    override suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
}