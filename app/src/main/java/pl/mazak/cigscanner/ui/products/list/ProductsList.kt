package pl.mazak.cigscanner.ui.products.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.AppViewModelProvider
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.BasicRoute

object ProductsListRoute : BasicRoute {
    override val route: String = "product"
    override val titleRes: Int = R.string.products_menu_title
}

@Composable
fun ProductsList(
    onAddClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductsListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val itemsState = viewModel.products.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = { CigScannerTopBar(stringResource(ProductsListRoute.titleRes)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .fillMaxSize()
        ) {
            if (itemsState.value.isEmpty()) {
                Text(
                    "Lista jest pusta",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(0.9f)
                ) {
                    items(itemsState.value) {
                        ProductEntry(
                            id = it.id.toString(),
                            name = it.name,
                            code = it.code,
                            price = it.price,
                            onEditClick = onEditClick,
                            viewModel = viewModel
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }

            Column(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = onAddClick,
                        modifier = Modifier.weight(0.8f)
                    ) {
                        Text(text = "Dodaj produkt")
                    }

                    Button(
                        onClick = onCameraClick,
                        modifier = Modifier.weight(0.2f)
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
}


@Composable
fun ProductEntry(
    id: String,
    name: String,
    code: String,
    price: String,
    onEditClick: (String) -> Unit,
    viewModel: ProductsListViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            ) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Text(text = code)
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "$price zł",
                Modifier.padding(end = 16.dp)
            )
            Button(
                onClick = { onEditClick(id) },
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.removeProduct(id.toInt())
                    }
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, contentDescription = null
                )
            }


        }
    }
}

@Preview
@Composable
fun ProductsListPreview() {
    ProductsList({}, {}, {})
}