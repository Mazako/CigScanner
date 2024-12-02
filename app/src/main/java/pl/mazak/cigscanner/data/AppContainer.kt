package pl.mazak.cigscanner.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import pl.mazak.cigscanner.data.api.NbpApiService
import pl.mazak.cigscanner.data.db.AppDatabase
import pl.mazak.cigscanner.data.db.currency.CurrencyOfflineRepository
import pl.mazak.cigscanner.data.db.currency.CurrencyRepository
import pl.mazak.cigscanner.data.db.product.OfflineProductsRepository
import pl.mazak.cigscanner.data.db.product.ProductsRepository
import retrofit2.Retrofit

interface AppContainer {
    val productsRepository: ProductsRepository
    val currencyRepository: CurrencyRepository
    val nbpApiService: NbpApiService
}

class AppDataContainer(private val context: Context): AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://api.nbp.pl/api/")
        .build()

    override val productsRepository: ProductsRepository by lazy {
        OfflineProductsRepository(AppDatabase.getDatabase(context).productDao())
    }

    override val currencyRepository: CurrencyRepository by lazy {
        CurrencyOfflineRepository(AppDatabase.getDatabase(context).currencyDao())
    }

    override val nbpApiService: NbpApiService by lazy {
        retrofit.create(NbpApiService::class.java)
    }
}