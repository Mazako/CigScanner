package pl.mazak.cigscanner.ui.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.AppViewModelProvider
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.BasicRoute
import pl.mazak.cigscanner.ui.theme.CigScannerTheme

object CurrencyPanelRoute : BasicRoute {
    override val route: String = "currency"
    override val titleRes: Int = R.string.course_menu_title

}

@Composable
fun CurrencyPanel(
    backCallback: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CurrencyPanelViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.currencyUiState
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        topBar = { CigScannerTopBar(stringResource(CurrencyPanelRoute.titleRes)) }
    ) { innerPadding ->
        CurrencyColumn(
            eur = uiState.eur,
            eurValid = uiState.eurValid,
            crown = uiState.crown,
            crownValid = uiState.crownValid,
            onEurChange = { viewModel.updateEur(it) },
            onCrownChange = { viewModel.updateCrown(it) },
            onSave = {
                coroutineScope.launch {
                    viewModel.update()
                    backCallback()
                }
            },
            innerPadding = innerPadding,
        )
    }
}

@Composable
fun CurrencyColumn(
    eur: String,
    eurValid: Boolean,
    crown: String,
    crownValid: Boolean,
    onEurChange: (String) -> Unit,
    onCrownChange: (String) -> Unit,
    onSave: () -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
            )
            .fillMaxSize()
    ) {
        TextField(
            value = eur,
            onValueChange = onEurChange,
            placeholder = { Text("EUR") },
            label = { Text("EUR -> PLN") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            isError = !eurValid
        )
        Spacer(Modifier.size(40.dp))
        TextField(
            value = crown,
            onValueChange = onCrownChange,
            placeholder = { Text("KCZ") },
            label = { Text("KCZ -> PLN") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            isError = !crownValid
        )
        Spacer(Modifier.size(40.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = eurValid && crownValid
        ) {
            Text("Zapisz")
        }

    }

}

@Preview
@Composable
fun CurrencyPanelPreview() {
    CigScannerTheme {
        CurrencyColumn(
            eur = "4.23",
            crown = "0.17",
            onEurChange = {},
            onCrownChange = {},
            onSave = {},
            innerPadding = PaddingValues(12.dp),
            eurValid = true,
            crownValid = true
        )
    }
}