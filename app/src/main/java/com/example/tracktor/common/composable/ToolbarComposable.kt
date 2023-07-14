package com.example.tracktor.common.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BasicToolbar(title:String) {
    TopAppBar(title = { Text(title) }, backgroundColor = getToolbarColor())

}

@Composable
fun OptionsToolbar(
    title:String,
    dropDownExtended:Boolean,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>
){

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
        }
    )
}

@Composable
fun SingleOptionToolbar(
    title:String,
    dropDownExtended:Boolean,
    onDismiss: ()->Unit,
    firstTitle:String,
    firstAction: ()->Unit,
){

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
    return if (darkTheme) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
}