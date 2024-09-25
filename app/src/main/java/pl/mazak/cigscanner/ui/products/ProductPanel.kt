package pl.mazak.cigscanner.ui.products

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun ProductPanel(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { CigScannerTopBar(stringResource(Routes.AddProduct.title)) }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = 16.dp
                )
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.weight(0.95f),
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Nazwa") },
                    label = { Text("Nazwa") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(32.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Kod") },
                        label = { Text("Kod") },
                        modifier = Modifier.weight(0.7f)
                    )
                    Spacer(Modifier.size(8.dp))
                    Button(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.outline_camera_alt_24),
                            contentDescription = null
                        )
                    }
                }
                Spacer(Modifier.height(32.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Cena") },
                    label = { Text("Cena") },
                    modifier = Modifier.fillMaxWidth()
                )

            }

            Column(
                modifier = Modifier.weight(0.05f)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Dodaj")
                }
            }
        }
    }
}

@Composable
@Preview
fun ProductPanelPreview() {
    ProductPanel()
}


