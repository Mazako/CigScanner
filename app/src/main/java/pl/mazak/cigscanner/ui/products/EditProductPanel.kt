package pl.mazak.cigscanner.ui.products

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.navigation.BasicRoute

object EditProductRoute: BasicRoute {
    override val route: String = "editProduct"
    override val titleRes: Int = R.string.edit_product_menu_title
    const val productIdArg: String = "id"
    val routeWithArgs = "$route/{$productIdArg}"
}

@Composable
fun EditProductPanel(
    backCallback: () -> Unit,
    modifier: Modifier = Modifier
) {

}