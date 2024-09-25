package pl.mazak.cigscanner.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import pl.mazak.cigscanner.CigScannerApplication
import pl.mazak.cigscanner.ui.products.ProductPanelViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProductPanelViewModel(
                cigScannerApplication().container.productsRepository
            )
        }
    }
}

fun CreationExtras.cigScannerApplication(): CigScannerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CigScannerApplication)
