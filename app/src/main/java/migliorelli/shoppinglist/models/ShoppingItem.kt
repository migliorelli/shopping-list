package migliorelli.shoppinglist.models

import java.util.UUID

data class ShoppingItem(
    val id: UUID,
    var name: String,
    var quantity: Int,
    var checked: Boolean = false,
    var address: String = ""
)