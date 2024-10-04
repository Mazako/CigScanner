package pl.mazak.cigscanner.ui.products.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.data.db.currency.Currency
import pl.mazak.cigscanner.data.db.currency.CurrencyRepository
import pl.mazak.cigscanner.data.db.product.Product
import pl.mazak.cigscanner.data.db.product.ProductsRepository
import pl.mazak.cigscanner.ui.products.ProductUiState
import java.math.BigDecimal
import java.math.RoundingMode

class ProductSearchViewModel(
    currencyRepository: CurrencyRepository,
    productsRepository: ProductsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val barcode: String = checkNotNull(savedStateHandle[ProductSearchPanelRoute.codeArg])

    val uiState: StateFlow<ProductSearchUiState> = combine(
        currencyRepository.getCurrency(),
        productsRepository.getProductByCodeStream(barcode)
    ) { curr, prod -> toState(prod, curr) }
        .stateIn(
            scope = viewModelScope,
            initialValue = ProductSearchUiState(),
            started = SharingStarted.WhileSubscribed(5_000L)
        )

    private fun toState(product: Product?, currency: Currency?): ProductSearchUiState {
        if (product == null) {
            return ProductSearchUiState(productExists = false, code = barcode)
        }
        if (currency == null) {
            return ProductSearchUiState(priceCalculated = false)
        }

        val plnPriceDecimal = BigDecimal.valueOf(product.plnPrice).setScale(10)
        val euroBigDecimal = BigDecimal.valueOf(currency.euro).setScale(10)
        val crownDecimal = BigDecimal.valueOf(currency.czechCrown).setScale(10)

        val euro = (plnPriceDecimal / euroBigDecimal).setScale(2, RoundingMode.HALF_UP).toString()
        val crown = (plnPriceDecimal / crownDecimal).setScale(2, RoundingMode.HALF_UP).toString()

        return ProductSearchUiState(
            name = product.name,
            plnPrice = product.plnPrice.toString(),
            code = product.code,
            eurPrice = euro,
            crownPrice = crown,
            productExists = true,
            priceCalculated = true
        )
    }
}


data class ProductSearchUiState(
    val name: String = "",
    val code: String = "",
    val plnPrice: String = "",
    val eurPrice: String = "",
    val crownPrice: String = "",
    val productExists: Boolean = false,
    val priceCalculated: Boolean = false
)