package pl.mazak.cigscanner.data.db.currency

import kotlinx.coroutines.flow.Flow


interface CurrencyRepository {
    fun getCurrency(): Flow<Currency?>
    suspend fun updateCurrency(currency: Currency)
    suspend fun insertCurrency(currency: Currency)
}