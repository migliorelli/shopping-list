package migliorelli.shoppinglist.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import migliorelli.shoppinglist.models.ShoppingItem


@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onCheckClick: (isChecked: Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                color = MaterialTheme.colorScheme.primary,
                width = 1.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Checkbox(
            checked = item.checked,
            onCheckedChange = onCheckClick
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp)
        ) {
            Text(text = item.name, fontWeight = FontWeight.Medium)
            Text(
                text = "${item.quantity} item${if (item.quantity.toInt() > 1) "s" else ""}",
            )
        }

        Spacer(modifier = Modifier.weight(1F))

        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "edit-button",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete-button",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}