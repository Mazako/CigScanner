package pl.mazak.cigscanner.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.mazak.cigscanner.ui.camera.CameraBarcodeReader
import pl.mazak.cigscanner.ui.camera.CameraBarcodeReaderRoute
import pl.mazak.cigscanner.ui.mainMenu.MainMenu
import pl.mazak.cigscanner.ui.mainMenu.MainMenuRoute
import pl.mazak.cigscanner.ui.products.AddProductCodeSingleton
import pl.mazak.cigscanner.ui.products.AddProductPanel
import pl.mazak.cigscanner.ui.products.AddProductRoute
import pl.mazak.cigscanner.ui.products.EditProductCodeSingleton
import pl.mazak.cigscanner.ui.products.EditProductPanel
import pl.mazak.cigscanner.ui.products.EditProductRoute
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
                onProductsClick = { navController.navigate(ProductsListRoute.route) }
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
                onCameraClick = { navController.navigate("${CameraBarcodeReaderRoute.route}?${CameraBarcodeReaderRoute.redirectParam}=${AddProductRoute.route}") }
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
                onCameraClick =  { navController.navigate("${CameraBarcodeReaderRoute.route}?${CameraBarcodeReaderRoute.redirectParam}=${AddProductRoute.selfCallback}") }
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
                onCameraClick = { navController.navigate("${CameraBarcodeReaderRoute.route}?${CameraBarcodeReaderRoute.redirectParam}=${EditProductRoute.route}") }
            )
        }
    }
}

