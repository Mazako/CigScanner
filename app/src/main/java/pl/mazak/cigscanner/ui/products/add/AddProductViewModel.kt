package pl.mazak.cigscanner.ui.products.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pl.mazak.cigscanner.data.db.product.ProductsRepository
import pl.mazak.cigscanner.ui.products.ProductUiState

object AddProductCodeSingleton {
    var CODE: String? = null
}

class AddProductViewModel(
    private val productsRepository: ProductsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val code: String? = savedStateHandle[AddProductRoute.codeArg]

    var productState by mutableStateOf(ProductUiState())
        private set


    init {
        code?.let {
            productState = productState.copy(code = it)
        }
    }

    fun updateName(name: String) {
        productState = productState.copy(name = name)
    }

    fun updateCode(code: String) {
        productState = productState.copy(code = code)
    }

    fun updatePrice(price: String) {
        productState = productState.copy(price = price)
    }

    suspend fun add() {
        productsRepository.addProduct(productState.toProduct())
    }

}

