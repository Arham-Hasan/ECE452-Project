package com.example.tracktor.common.composable


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasicDropdown(
    options: List<Pair<String, String>>,
    action: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier

) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Please Select") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        Modifier
            .padding(8.dp)
            .then(modifier) // add modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2F6F3D),
                textColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = selectionOption.first
                        expanded = false
                        action(selectionOption.second)
                    }
                ) {
                    Text(text = selectionOption.first)
                }
            }
        }
    }
}

