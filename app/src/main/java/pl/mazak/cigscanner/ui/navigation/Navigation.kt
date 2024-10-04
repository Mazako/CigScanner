package pl.mazak.cigscanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.mazak.cigscanner.ui.camera.CameraBarcodeReader
import pl.mazak.cigscanner.ui.camera.CameraBarcodeReaderRoute
import pl.mazak.cigscanner.ui.currency.CurrencyPanel
import pl.mazak.cigscanner.ui.currency.CurrencyPanelRoute
import pl.mazak.cigscanner.ui.mainMenu.MainMenu
import pl.mazak.cigscanner.ui.mainMenu.MainMenuRoute
import pl.mazak.cigscanner.ui.products.add.AddProductCodeSingleton
import pl.mazak.cigscanner.ui.products.add.AddProductPanel
import pl.mazak.cigscanner.ui.products.add.AddProductRoute
import pl.mazak.cigscanner.ui.products.edit.EditProductCodeSingleton
import pl.mazak.cigscanner.ui.products.edit.EditProductPanel
import pl.mazak.cigscanner.ui.products.edit.EditProductRoute
import pl.mazak.cigscanner.ui.products.list.ProductsList
import pl.mazak.cigscanner.ui.products.list.ProductsListRoute

@Composable
fun CigScannerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = MainMenuRoute.route,
        modifier = modifier
    ) {
        composable(route = MainMenuRoute.route) {
            MainMenu(
                onProductsClick = { navController.navigate(ProductsListRoute.route) },
                onCurrencyClick = { navController.navigate(CurrencyPanelRoute.route) }
            )
        }

        composable(
            route = CameraBarcodeReaderRoute.routeWithParam,
            arguments = listOf(navArgument(CameraBarcodeReaderRoute.redirectParam) {
                type = NavType.StringType
                nullable = true
            })
        ) { backstackEntry ->
            CameraBarcodeReader(
                imageProcessedCallback = {
                    val route =
                        backstackEntry.arguments?.getString(CameraBarcodeReaderRoute.redirectParam)
                    when (route) {
                        AddProductRoute.route -> navController.navigate("${AddProductRoute.route}?${AddProductRoute.codeArg}=${it}") {
                            popUpTo(CameraBarcodeReaderRoute.route) {
                                inclusive = true
                            }
                            restoreState = true
                        }

                        AddProductRoute.selfCallback -> {
                            AddProductCodeSingleton.CODE = it
                            navController.popBackStack()
                        }

                        EditProductRoute.route -> {
                            EditProductCodeSingleton.CODE = it
                            navController.popBackStack()
                        }

                        else -> navController.popBackStack()
                    }
                }
            )
        }

        composable(route = ProductsListRoute.route) {
            ProductsList(
                onAddClick = { navController.navigate(AddProductRoute.route) },
                onEditClick = { navController.navigate("${EditProductRoute.route}/$it") },
                onCameraClick = { navController.navigate("${CameraBarcodeReaderRoute.route}?${CameraBarcodeReaderRoute.redirectParam}=${AddProductRoute.route}") },
                navigateUpCallback = { navController.navigateUp() }
            )
        }

        composable(
            route = AddProductRoute.routeWithArgs,
            arguments = listOf(navArgument(AddProductRoute.codeArg) {
                type = NavType.StringType
                nullable = true
            })
        ) {
            AddProductPanel(
                backCallback = { navController.popBackStack() },
                onCameraClick = { navController.navigate("${CameraBarcodeReaderRoute.route}?${CameraBarcodeReaderRoute.redirectParam}=${AddProductRoute.selfCallback}") },
                navigateUpCallback = { navController.navigateUp() }
            )
        }

        composable(
            route = EditProductRoute.routeWithArgs,
            arguments = listOf(
                navArgument(EditProductRoute.productIdArg) {
                    type = NavType.IntType
                },
            )
        ) {
            EditProductPanel(
                backCallback = { navController.popBackStack() },
                onCameraClick = { navController.navigate("${CameraBarcodeReaderRoute.route}?${CameraBarcodeReaderRoute.redirectParam}=${EditProductRoute.route}") },
                navigateUpCallback = { navController.navigateUp() }
            )
        }

        composable(
            route = CurrencyPanelRoute.route
        ) {
            CurrencyPanel(
                backCallback = { navController.popBackStack() },
                navigateUpCallback = { navController.navigateUp() }
            )
        }
    }
}

