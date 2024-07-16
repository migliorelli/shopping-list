package migliorelli.shoppinglist.ui.screens

import AddItemDialog
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import migliorelli.locationapp.utils.LocationUtils
import migliorelli.recipes.models.Screen
import migliorelli.shoppinglist.MainActivity
import migliorelli.shoppinglist.models.ShoppingItem
import migliorelli.shoppinglist.ui.components.ShoppingListItem
import migliorelli.shoppinglist.ui.dialogs.EditItemDialog
import migliorelli.shoppinglist.viewmodels.LocationViewModel

@Composable
fun ShoppingListScreen(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    address: String
) {

    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showAddItemDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingItem?>(null) }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                ) {

                    locationUtils.requestLocationUpdates(viewModel = viewModel)
                } else {
                    // i dont have access
                    val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                        context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        context, Manifest.permission.ACCESS_COARSE_LOCATION

                    )

                    if (rationaleRequired) {
                        Toast.makeText(
                            context,
                            "Location Permission is required for this feature to work",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Location Permission is required. Plase enable in the Android Settings.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Shopping List",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = { showAddItemDialog = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .scale(1.2f)
        ) {
            Text("Add item")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(shoppingItems) {
                ShoppingListItem(
                    item = it,
                    onCheckClick = { isChecked ->
                        val index = shoppingItems.indexOfFirst { item -> item.id == it.id }

                        if (index != -1) {
                            val newItem = it.copy(checked = isChecked)
                            shoppingItems =
                                shoppingItems.toMutableList().apply { set(index, newItem) }
                        }
                    },
                    onDeleteClick = {
                        shoppingItems = shoppingItems.filterNot { item -> item.id == it.id }
                    },
                    onEditClick = {
                        viewModel.setAddress(it.address)
                        editingItem = it
                    }
                )
            }
        }
    }

    AddItemDialog(
        show = showAddItemDialog,
        address = address,
        onRequestClose = {
            showAddItemDialog = false
            viewModel.setAddress(null)
        },
        onLocationButton = {
            if (locationUtils.hasLocationPermission(context)) {

                locationUtils.requestLocationUpdates(viewModel)
                navController.navigate(Screen.LocationSelectionScreen.route) {
                    this.launchSingleTop
                }

            } else {

                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

            }
        },
        onAddItem = {
            shoppingItems = shoppingItems + it
        }
    )

    EditItemDialog(
        show = editingItem != null,
        originalItem = editingItem, address = address,
        onRequestClose = {
            editingItem = null
            viewModel.setAddress(null)
        },
        onEditItem = {
            val index = shoppingItems.indexOfFirst { item -> item.id == it.id }

            if (index != -1) {
                shoppingItems =
                    shoppingItems.toMutableList().apply { set(index, it) }
            }
        },
        onLocationButton = {
            if (locationUtils.hasLocationPermission(context)) {

                locationUtils.requestLocationUpdates(viewModel)
                navController.navigate(Screen.LocationSelectionScreen.route) {
                    this.launchSingleTop
                }

            } else {

                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

            }
        },
    )
}
