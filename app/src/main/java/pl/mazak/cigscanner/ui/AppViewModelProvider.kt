package pl.mazak.cigscanner.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pl.mazak.cigscanner.CigScannerApplication
import pl.mazak.cigscanner.ui.products.AddProductViewModel
import pl.mazak.cigscanner.ui.products.EditProductViewModel
import pl.mazak.cigscanner.ui.products.ProductsListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AddProductViewModel(
                cigScannerApplication().container.productsRepository
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
    }
}

fun CreationExtras.cigScannerApplication(): CigScannerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CigScannerApplication)
