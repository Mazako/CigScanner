package pl.mazak.cigscanner.ui.products.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.data.db.product.ProductsRepository
import pl.mazak.cigscanner.ui.products.ProductUiState
import pl.mazak.cigscanner.ui.products.toUiState

object EditProductCodeSingleton{
    var CODE: String? = null
}

class EditProductViewModel(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository,
): ViewModel() {
    private val id: Int = checkNotNull(savedStateHandle[EditProductRoute.productIdArg])

    private val _productState = MutableStateFlow(ProductUiState())
    val productUiState = _productState.asStateFlow()

    init {
        viewModelScope.launch {
            _productState.value = productsRepository.getProductById(id).first().toUiState()
        }
    }

    fun updateName(name: String) {
        _productState.value = _productState.value.copy(
            name = name
        )
    }

    fun updateCode(code: String) {
        _productState.value = _productState.value.copy(
            code = code
        )
    }

    fun updatePrice(price: String) {
        _productState.value = _productState.value.copy(
            price = price
        )
    }

    suspend fun save() {
        productsRepository.updateProduct(_productState.value.toProduct())
    }

}