package pl.mazak.cigscanner.ui.products.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pl.mazak.cigscanner.data.ProductsRepository
import pl.mazak.cigscanner.ui.products.ProductUiState
import pl.mazak.cigscanner.ui.products.toUiState

class ProductsListViewModel(private val productsRepository: ProductsRepository): ViewModel() {

    val products: StateFlow<List<ProductUiState>> = productsRepository.getAllProductsStream()
        .map {
            it.map { p ->
                p.toUiState()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    suspend fun removeProduct(id: Int) {
        productsRepository.removeProductById(id)
    }
}