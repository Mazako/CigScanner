package pl.mazak.cigscanner.data

import android.content.Context
import androidx.core.os.persistableBundleOf

interface AppContainer {
    val productsRepository: ProductsRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(AppDatabase.getDatabase(context).productDao())
    }
}