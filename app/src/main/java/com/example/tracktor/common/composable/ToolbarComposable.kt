package com.example.tracktor.common.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BasicToolbar(title: String) {
    TopAppBar(title = { Text(title) }, backgroundColor = getToolbarColor())

}

@Composable
fun OptionsToolbar(
    title: String,
    dropDownExtended: Boolean,
    toggleDropDown: () -> Unit,
    dropDownOptions: List<Pair<String, () -> Unit>>,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        title = { Text(title) },
        backgroundColor = getToolbarColor(),
        actions = {
            IconButton(onClick = toggleDropDown) {
                Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "Options")
            }

            DropdownMenu(expanded = dropDownExtended, onDismissRequest = toggleDropDown) {
                dropDownOptions.forEach { dropDownOption ->
                    DropdownMenuItem(onClick = {
                        dropDownOption.second()
                        toggleDropDown()
                    }) {
                        Text(dropDownOption.first)
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun SingleOptionToolbar(
    title: String,
    dropDownExtended: Boolean,
    onDismiss: () -> Unit,
    firstTitle: String,
    firstAction: () -> Unit,
) {

    TopAppBar(
        title = { Text(title) },
        backgroundColor = getToolbarColor(),
        actions = {
            DropdownMenu(expanded = dropDownExtended, onDismissRequest = onDismiss) {
                DropdownMenuItem(onClick = firstAction) {
                    Text(firstTitle)
                }
            }
        }
    )
}

@Composable
fun getToolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
    return if (darkTheme) Color(0xFF2F6F3D) else Color(0xFFB5E6C1)
}