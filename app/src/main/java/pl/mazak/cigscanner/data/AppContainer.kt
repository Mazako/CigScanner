package pl.mazak.cigscanner.data

import android.content.Context
import pl.mazak.cigscanner.data.db.AppDatabase
import pl.mazak.cigscanner.data.db.currency.CurrencyOfflineRepository
import pl.mazak.cigscanner.data.db.currency.CurrencyRepository
import pl.mazak.cigscanner.data.db.product.OfflineProductsRepository
import pl.mazak.cigscanner.data.db.product.ProductsRepository

interface AppContainer {
    val productsRepository: ProductsRepository
    val currencyRepository: CurrencyRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(AppDatabase.getDatabase(context).productDao())
    }

    override val currencyRepository: CurrencyRepository by lazy {
        CurrencyOfflineRepository(AppDatabase.getDatabase(context).currencyDao())
    }
}