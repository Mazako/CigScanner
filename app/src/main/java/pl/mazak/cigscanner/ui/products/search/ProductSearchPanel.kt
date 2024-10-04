package pl.mazak.cigscanner.ui.products.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.AppViewModelProvider
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.BasicRoute
import pl.mazak.cigscanner.ui.products.edit.EditProductRoute
import pl.mazak.cigscanner.ui.theme.CigScannerTheme


object ProductSearchPanelRoute : BasicRoute {
    override val route: String = "search"
    const val codeArg: String = "code"
    val routeWithArgs: String = "$route/{$codeArg}"
    override val titleRes: Int = R.string.search_menu_title

}

@Composable
fun ProductSearchPanel(
    cameraCallback: () -> Unit,
    navigateUpCallback: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    viewModel: ProductSearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val uiState = viewModel.uiState.collectAsState()

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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            if (!uiState.value.productExists) {
                ProductNotExistsMessage(
                    code = uiState.value.code,
                    modifier = Modifier.weight(0.9f)
                )
            } else if (!uiState.value.priceCalculated) {
                CurrencyNotExistsMessage(
                    modifier = Modifier.weight(0.9f)
                )
            } else {
                ProductSearchContent(
                    name = uiState.value.name,
                    code = uiState.value.code,
                    plnPrice = uiState.value.plnPrice,
                    eurPrice = uiState.value.eurPrice,
                    crownPrice = uiState.value.crownPrice,
                    modifier = Modifier.weight(0.9f)
                )
            }


            Column(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = cameraCallback,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_camera_alt_24),
                        contentDescription = null
                    )
                }
            }

        }
    }

}


@Composable
fun ProductSearchContent(
    modifier: Modifier = Modifier,
    name: String,
    code: String,
    plnPrice: String,
    eurPrice: String,
    crownPrice: String
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Produkt: $name",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Kod: $code",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Cena w złotówkach: $plnPrice PLN",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Cena w euro: $eurPrice €",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Cena w koronach: $crownPrice CZK",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


    }
}

@Composable
fun ProductNotExistsMessage(
    code: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Nie znaleziono produktu o z kodem: $code",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CurrencyNotExistsMessage(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Ceny nie zostały wyliczone",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Wróć do menu głównego żeby ustawić kursy walut",
            textAlign = TextAlign.Justify,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Preview
@Composable
fun productSearchContentPreview() {
    CigScannerTheme {
        ProductSearchContent(
            name = "Marlboro gold",
            code = "22211133",
            plnPrice = "20.22",
            eurPrice = "4",
            crownPrice = "100"
        )
    }
}

@Preview
@Composable
fun ProductNotExistsMessagePreview() {
    CigScannerTheme {
        ProductNotExistsMessage(
            code = "2111133"
        )
    }
}

@Preview
@Composable
fun CurrencyNotExistsMessagePreview() {
    CigScannerTheme {
        CurrencyNotExistsMessage()
    }
}

