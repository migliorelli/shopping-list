package migliorelli.shoppinglist.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import migliorelli.shoppinglist.models.ShoppingItem
import java.util.UUID


@Composable
fun AddItemDialog(
    show: Boolean,
    onRequestClose: () -> Unit,
    onAddItem: (newItem: ShoppingItem) -> Unit
) {

    var nameField by remember { mutableStateOf("") }
    var nameFieldError by remember { mutableStateOf(false) }

    var quantityField by remember { mutableStateOf("") }
    var quantityFieldError by remember { mutableStateOf(false) }

    fun closeDialog() {
        onRequestClose()
        nameField = ""
        quantityField = "";
    }

    if (show) {
        AlertDialog(
            onDismissRequest = { closeDialog() },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {

                        nameFieldError = false;
                        quantityFieldError = false;

                        if (nameField.isBlank()) {
                            nameFieldError = true;
                        } else if (quantityField.isBlank()) {
                            quantityFieldError = true;
                        } else {
                            val newItem = ShoppingItem(
                                id = UUID.randomUUID(),
                                name = nameField,
                                quantity = quantityField.toInt()
                            )

                            onAddItem(newItem)
                            closeDialog()
                        }


                    }) {
                        Text("Add")
                    }

                    Button(onClick = { closeDialog() }) {
                        Text("Cancel")
                    }
                }
            },
            title = { Text("Add shopping item") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nameField,
                        onValueChange = { nameField = it },
                        isError = nameFieldError,
                        supportingText = {
                            if (nameFieldError) {
                                Text(
                                    text = "Name can't be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        label = { Text("Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = quantityField,
                        label = { Text("Quantity") },
                        isError = quantityFieldError,
                        supportingText = {
                            if (quantityFieldError) {
                                Text(
                                    text = "Quantity can't be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        onValueChange = {
                            if (it.toIntOrNull() != null) {
                                quantityField = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )

                }
            }
        )
    }
}

@Composable
fun EditItemDialog(
    show: Boolean,
    originalItem: ShoppingItem?,
    onRequestClose: () -> Unit,
    onEditItem: (newItem: ShoppingItem) -> Unit
) {
    if (show && originalItem != null) {
        var nameField by remember { mutableStateOf(originalItem.name) }
        var nameFieldError by remember { mutableStateOf(false) }

        var quantityField by remember { mutableStateOf(originalItem.quantity.toString()) }
        var quantityFieldError by remember { mutableStateOf(false) }

        fun closeDialog() {
            onRequestClose()
            nameField = ""
            quantityField = "";
        }


        AlertDialog(
            onDismissRequest = { closeDialog() },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {

                        nameFieldError = false;
                        quantityFieldError = false;

                        if (nameField.isBlank()) {
                            nameFieldError = true;
                        } else if (quantityField.isBlank()) {
                            quantityFieldError = true;
                        } else {
                            val newItem =
                                originalItem.copy(
                                    name = nameField,
                                    quantity = quantityField.toIntOrNull() ?: 1
                                )

                            onEditItem(newItem)
                            closeDialog()
                        }


                    }) {
                        Text("Edit")
                    }

                    Button(onClick = { closeDialog() }) {
                        Text("Cancel")
                    }
                }
            },
            title = { Text("Edit shopping item") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nameField,
                        onValueChange = { nameField = it },
                        isError = nameFieldError,
                        supportingText = {
                            if (nameFieldError) {
                                Text(
                                    text = "Name can't be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        label = { Text("Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = quantityField,
                        label = { Text("Quantity") },
                        isError = quantityFieldError,
                        supportingText = {
                            if (quantityFieldError) {
                                Text(
                                    text = "Quantity can't be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        onValueChange = {
                            if (it.toIntOrNull() != null) {
                                quantityField = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )

                }
            }
        )
    }
}