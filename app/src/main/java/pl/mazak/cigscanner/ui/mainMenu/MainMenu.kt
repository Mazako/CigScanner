package pl.mazak.cigscanner.ui.mainMenu

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import pl.mazak.cigscanner.ui.CigScannerTopBar
import pl.mazak.cigscanner.ui.navigation.Routes


@Composable
fun MainMenu(modifier: Modifier = Modifier) {

    Scaffold(
        modifier = modifier,
        topBar = { CigScannerTopBar(stringResource(Routes.MainMenu.title)) }
    ) { innerPadding ->
        Text(
            text = "Siema",
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
            )
        )
    }

}