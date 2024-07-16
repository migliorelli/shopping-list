package migliorelli.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import migliorelli.locationapp.utils.LocationUtils
import migliorelli.recipes.models.Screen
import migliorelli.shoppinglist.ui.screens.LocationSelectionScreen
import migliorelli.shoppinglist.ui.screens.ShoppingListScreen
import migliorelli.shoppinglist.viewmodels.LocationViewModel

@Composable
fun ShoppingList() {
    val navController = rememberNavController()
    val viewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(navController = navController, startDestination = Screen.ShoppingListScreen.route) {
        composable(Screen.ShoppingListScreen.route) {
            ShoppingListScreen(
                locationUtils,
                viewModel,
                navController,
                context,
                viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
            )
        }

        dialog(Screen.LocationSelectionScreen.route) { backstack ->
            viewModel.location.value?.let { location ->
                LocationSelectionScreen(location = location, onLocationSelected = { locationData ->
                    viewModel.fetchAddress("${locationData.latitude},${locationData.longitude}")
                    navController.popBackStack()
                })
            }
        }
    }
}
