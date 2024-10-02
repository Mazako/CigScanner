package pl.mazak.cigscanner.ui.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.mazak.cigscanner.data.ProductsRepository

class AddProductViewModel(private val productsRepository: ProductsRepository): ViewModel() {

    var productState by mutableStateOf(ProductUiState())
        private set


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

