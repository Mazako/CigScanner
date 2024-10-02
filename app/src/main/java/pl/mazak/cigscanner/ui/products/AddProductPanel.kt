package pl.mazak.cigscanner.ui.products

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

object AddProductRoute : BasicRoute {
    override val route: String = "addProduct"
    override val titleRes: Int = R.string.add_product_menu_title
}


@Composable
fun AddProductPanel(
    backCallback: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.productState
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        topBar = { CigScannerTopBar(stringResource(AddProductRoute.titleRes)) }
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
            innerPadding = innerPadding
        )
    }
}

@Composable
@Preview
fun ProductPanelPreview() {
}


