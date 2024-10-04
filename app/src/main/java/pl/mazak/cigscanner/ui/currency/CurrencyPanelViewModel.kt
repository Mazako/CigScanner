package pl.mazak.cigscanner.ui.currency

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.data.db.currency.Currency
import pl.mazak.cigscanner.data.db.currency.CurrencyRepository

class CurrencyPanelViewModel(private val currencyRepository: CurrencyRepository) : ViewModel() {

    var currencyUiState by mutableStateOf(CurrencyUiState())
        private set
    private var currencyExists: Boolean = false

    init {
        viewModelScope.launch {
            currencyRepository.getCurrency().collect {
                it?.let {
                    currencyUiState =
                        CurrencyUiState(it.euro.toString(), true, it.czechCrown.toString(), true)
                    currencyExists = true
                }
            }
        }
    }

    fun updateEur(value: String) {
        currencyUiState = currencyUiState.copy(eur = value, eurValid = isCurrencyValid(value))
    }

    fun updateCrown(value: String) {
        currencyUiState = currencyUiState.copy(crown = value, crownValid = isCurrencyValid(value))
    }

    suspend fun update() {
        if (!currencyUiState.eurValid || !currencyUiState.crownValid) {
            return
        }
        if (currencyExists) {
            currencyRepository.updateCurrency(currencyUiState.toCurrency())
        } else {
            currencyRepository.insertCurrency(currencyUiState.toCurrency())
        }
    }

    private fun isCurrencyValid(value: String): Boolean {
        try {
            val v = value.toDouble()
            return v > 0
        } catch (e: NumberFormatException) {
            return false
        }
    }

}

data class CurrencyUiState(
    val eur: String = "",
    val eurValid: Boolean = false,
    val crown: String = "",
    val crownValid: Boolean = false
) {
    fun toCurrency(): Currency {
        return Currency(id = 1, euro = eur.toDouble(), czechCrown = crown.toDouble())
    }
}