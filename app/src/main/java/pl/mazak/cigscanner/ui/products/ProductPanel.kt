package pl.mazak.cigscanner.ui.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import pl.mazak.cigscanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPanel(
    name: String,
    onNameChange: (String) -> Unit,
    code: String,
    onCodeChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    buttonName: String,
    onDone: () -> Unit,
    onCameraClick: () -> Unit = {},
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val showDialog = remember { mutableStateOf(false) }

    val safeDoneWrapper = {
        if (name.isEmpty() || code.isEmpty() || price.isEmpty()) {
            showDialog.value = true
        } else {
            onDone()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = innerPadding.calculateBottomPadding()
            )
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.weight(0.9f),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = name,
                onValueChange = onNameChange,
                placeholder = { Text("Nazwa") },
                label = { Text("Nazwa") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(32.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = code,
                    onValueChange = onCodeChange,
                    placeholder = { Text("Kod") },
                    label = { Text("Kod") },
                    modifier = Modifier.weight(0.7f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(Modifier.size(8.dp))
                Button(
                    onClick = onCameraClick,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_camera_alt_24),
                        contentDescription = null
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            TextField(
                value = price,
                onValueChange = onPriceChange,
                placeholder = { Text("Cena") },
                label = { Text("Cena") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )
        }

        Column(
            modifier = Modifier.weight(0.1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = safeDoneWrapper,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(buttonName)
            }
        }
        if (showDialog.value) {
            BasicAlertDialog(
                onDismissRequest = { showDialog.value = false },
                properties = DialogProperties()
            ) {
                Surface(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = AlertDialogDefaults.TonalElevation
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Formularz zawiera puste wartości. Uzupełnij je, aby dodać produkt"
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        TextButton(
                            onClick = { showDialog.value = false },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Ok")
                        }
                    }
                }
            }
        }
    }
}
