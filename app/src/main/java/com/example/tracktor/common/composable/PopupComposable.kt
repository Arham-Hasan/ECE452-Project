package com.example.tracktor.common.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tracktor.data.model.InventoryItem

@Composable
fun ModifyItem(toggleAlert:()->Unit, onSave:(String, String) -> Unit, AlertVisible:Boolean, item:InventoryItem){
    val price = remember { mutableStateOf(item.itemPrice.toString()) }
    val quantity = remember { mutableStateOf(item.itemTotal.toString()) }
    if (AlertVisible) {
        AlertDialog(
            onDismissRequest =
            toggleAlert,
            title = {
                Text(text = "Editing ${item.name}")
            },

            text = {
                Column {
                    NumberField(text = "1.23", value = price.value, onNewValue = {newValue -> price.value = newValue}, label = "Price", moneyField = true)

                    Spacer(modifier = Modifier.height(16.dp))
                    NumberField(text = "123", value = quantity.value, onNewValue = {newValue -> quantity.value = newValue}, label = "Quantity")

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        toggleAlert()
                        onSave(price.value, quantity.value)
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        toggleAlert()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
