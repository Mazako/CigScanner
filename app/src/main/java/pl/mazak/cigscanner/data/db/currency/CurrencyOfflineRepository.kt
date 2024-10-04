package pl.mazak.cigscanner.data.db.currency

import kotlinx.coroutines.flow.Flow

class CurrencyOfflineRepository(private val currencyDao: CurrencyDao): CurrencyRepository {
    override fun getCurrency(): Flow<Currency?> = currencyDao.getCurrency()
    override suspend fun updateCurrency(currency: Currency) = currencyDao.update(currency)
}