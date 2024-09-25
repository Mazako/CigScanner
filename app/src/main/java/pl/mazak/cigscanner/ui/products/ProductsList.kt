package pl.mazak.cigscanner.ui.products

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.Routes

@Composable
fun ProductsList(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { CigScannerTopBar(stringResource(Routes.Products.title)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = 16.dp
                )
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(0.95f)
            ) {
                ProductEntry(
                    name = "Marlboro Gold",
                    code = "1111222333",
                    price = 13.23
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.05f)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(0.8f)
                    ) {
                        Text(text = "Dodaj produkt")
                    }

                    Button(
                        onClick = {},
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
    name: String,
    code: String,
    price: Double,
    modifier: Modifier = Modifier
) {
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
                Text(text = name)
                Spacer(Modifier.weight(1f))
                Text(text = code)
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "$price zł",
                Modifier.padding(end = 16.dp)
            )
            Button(
                onClick = {},
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            Button(
                onClick = {},
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
    ProductsList()
}