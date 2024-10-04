package pl.mazak.cigscanner.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pl.mazak.cigscanner.CigScannerApplication
import pl.mazak.cigscanner.ui.currency.CurrencyPanelViewModel
import pl.mazak.cigscanner.ui.products.add.AddProductViewModel
import pl.mazak.cigscanner.ui.products.edit.EditProductViewModel
import pl.mazak.cigscanner.ui.products.list.ProductsListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AddProductViewModel(
                cigScannerApplication().container.productsRepository,
                this.createSavedStateHandle()
            )
        }

        initializer {
            ProductsListViewModel(
                cigScannerApplication().container.productsRepository
            )
        }

        initializer {
            EditProductViewModel(
                this.createSavedStateHandle(),
                cigScannerApplication().container.productsRepository
            )
        }

        initializer {
            CurrencyPanelViewModel(
                cigScannerApplication().container.currencyRepository
            )
        }
    }
}

fun CreationExtras.cigScannerApplication(): CigScannerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CigScannerApplication)
