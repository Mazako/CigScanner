package pl.mazak.cigscanner.ui.products.edit

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.AppViewModelProvider
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.BasicRoute
import pl.mazak.cigscanner.ui.products.ProductPanel
import pl.mazak.cigscanner.ui.products.list.ProductsListRoute

object EditProductRoute : BasicRoute {
    override val route: String = "editProduct"
    override val titleRes: Int = R.string.edit_product_menu_title
    const val productIdArg: String = "id"
    val routeWithArgs = "$route/{$productIdArg}"
}

@Composable
fun EditProductPanel(
    backCallback: () -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigateUpCallback: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: EditProductViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.productUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (EditProductCodeSingleton.CODE != null) {
            viewModel.updateCode(EditProductCodeSingleton.CODE!!)
            EditProductCodeSingleton.CODE = null
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CigScannerTopBar(
                title = stringResource(EditProductRoute.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUpCallback,
            )
        }
    ) { innerPadding ->
        ProductPanel(
            name = uiState.value.name,
            onNameChange = { viewModel.updateName(it) },
            price = uiState.value.price,
            onPriceChange = { viewModel.updatePrice(it) },
            code = uiState.value.code,
            onCodeChange = { viewModel.updateCode(it) },
            buttonName = "Zapisz",
            onDone = {
                coroutineScope.launch {
                    viewModel.save()
                    backCallback()
                }
            },
            onCameraClick = onCameraClick,
            innerPadding = innerPadding
        )
    }
}