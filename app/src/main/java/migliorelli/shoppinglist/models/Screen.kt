package migliorelli.recipes.models

sealed class Screen(val route: String) {

    data object ShoppingListScreen : Screen("shoppinglistscreen")
    data object LocationSelectionScreen : Screen("locationselectionscreen")

}