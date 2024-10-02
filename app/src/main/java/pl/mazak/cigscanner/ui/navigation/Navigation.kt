package pl.mazak.cigscanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.mazak.cigscanner.ui.CameraPreview
import pl.mazak.cigscanner.ui.mainMenu.MainMenu
import pl.mazak.cigscanner.ui.mainMenu.MainMenuRoute
import pl.mazak.cigscanner.ui.products.AddProductPanel
import pl.mazak.cigscanner.ui.products.AddProductRoute
import pl.mazak.cigscanner.ui.products.ProductsList
import pl.mazak.cigscanner.ui.products.ProductsListRoute

@Composable
fun CigScannerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = MainMenuRoute.route,
        modifier = modifier
    ) {
        composable(route = MainMenuRoute.route) {
            MainMenu(
                onCameraClick = { navController.navigate("cam") },
                onProductsClick = {navController.navigate(ProductsListRoute.route)}
            )
        }

        composable(route = "cam") {
            CameraPreview()
        }

        composable(route = ProductsListRoute.route) {
            ProductsList(
                onAddClick = { navController.navigate(AddProductRoute.route) }
            )
        }

        composable(route = AddProductRoute.route) {
            AddProductPanel(
                backCallback = {navController.popBackStack()}
            )
        }
    }
}

