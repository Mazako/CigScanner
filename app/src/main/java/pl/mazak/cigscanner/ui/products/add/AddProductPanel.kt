package pl.mazak.cigscanner.ui.products.add

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.AppViewModelProvider
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.BasicRoute
import pl.mazak.cigscanner.ui.products.ProductPanel
import pl.mazak.cigscanner.ui.products.edit.EditProductRoute

object AddProductRoute : BasicRoute {
    override val route: String = "addProduct"
    const val codeArg: String = "code"
    val routeWithArgs: String = "$route?$codeArg={$codeArg}"
    override val titleRes: Int = R.string.add_product_menu_title
    val selfCallback: String = "addProductSELF"
}

@Composable
fun AddProductPanel(
    backCallback: () -> Unit,
    onCameraClick: () -> Unit,
    navigateUpCallback: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: AddProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.productState
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (AddProductCodeSingleton.CODE != null) {
            viewModel.updateCode(AddProductCodeSingleton.CODE!!)
            AddProductCodeSingleton.CODE = null
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CigScannerTopBar(
                title = stringResource(AddProductRoute.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUpCallback,
            )
        }
    ) { innerPadding ->
        ProductPanel(
            name = uiState.name,
            onNameChange = viewModel::updateName,
            code = uiState.code,
            onCodeChange = viewModel::updateCode,
            price = uiState.price,
            onPriceChange = viewModel::updatePrice,
            onDone = {
                coroutineScope.launch {
                    viewModel.add()
                    backCallback()
                }
            },
            buttonName = "Dodaj",
            onCameraClick = onCameraClick,
            innerPadding = innerPadding
        )
    }
}

@Composable
@Preview
fun ProductPanelPreview() {
    AddProductPanel(backCallback = {}, {}, {})
}


