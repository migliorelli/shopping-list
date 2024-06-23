package migliorelli.shoppinglist

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
import migliorelli.shoppinglist.models.ShoppingItem
import migliorelli.shoppinglist.ui.components.ShoppingListItem
import migliorelli.shoppinglist.ui.dialogs.AddItemDialog
import migliorelli.shoppinglist.ui.dialogs.EditItemDialog

@Composable
fun ShoppingListApp() {

    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showAddItemDialog by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingItem?>(null) }

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
                        editingItem = it
                    }
                )
            }
        }
    }

    AddItemDialog(
        show = showAddItemDialog,
        onRequestClose = { showAddItemDialog = false },
        onAddItem = {
            shoppingItems = shoppingItems + it
        }
    )

    EditItemDialog(
        show = editingItem != null,
        originalItem = editingItem,
        onRequestClose = { editingItem = null },
        onEditItem = {
            val index = shoppingItems.indexOfFirst { item -> item.id == it.id }

            if (index != -1) {
                shoppingItems =
                    shoppingItems.toMutableList().apply { set(index, it) }
            }
        }
    )
}
