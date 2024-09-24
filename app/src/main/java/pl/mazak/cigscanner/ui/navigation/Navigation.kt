package pl.mazak.cigscanner.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.mazak.cigscanner.R
import pl.mazak.cigscanner.ui.CameraPreview
import pl.mazak.cigscanner.ui.mainMenu.MainMenu

@Composable
fun CigScannerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Routes.MainMenu.name,
        modifier = modifier
    ) {
        composable(route = Routes.MainMenu.name) {
            MainMenu(onCameraClick = { navController.navigate(Routes.Camera.name) })
        }

        composable(route = Routes.Camera.name) {
            CameraPreview()
        }
    }
}

enum class Routes(@StringRes val title: Int) {
    MainMenu(R.string.main_menu_title),
    Camera(R.string.camera_menu_title)
}
