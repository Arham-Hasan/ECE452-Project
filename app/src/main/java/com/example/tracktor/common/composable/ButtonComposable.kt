package com.example.tracktor.common.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextButton(text:String, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) { Text(text = text) }
}

@Composable
fun BasicButton(text:String, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun MicButton(text:String, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}
@Composable
fun CreateFarmButton(action: () -> Unit, modifier: Modifier= Modifier){
    ExtendedFloatingActionButton(
        onClick = action,
        text = {Text("Create a Farm")},
        modifier = modifier.padding(8.dp),
        backgroundColor = getToolbarColor()
    )
}

@Composable
fun JoinFarmButton(action: () -> Unit, modifier: Modifier= Modifier){
    ExtendedFloatingActionButton(
        onClick = action,
        text = {Text("Join a Farm")},
        modifier = modifier.padding(8.dp),
        backgroundColor = getToolbarColor()
    )
}

@Composable
fun ExpandableButton(action: () -> Unit, modifier: Modifier= Modifier, expanded: Boolean){
    FloatingActionButton(
        onClick = action,
        modifier = modifier.padding(8.dp).size(48.dp),
        containerColor = getToolbarColor(),
    ){
        Icon(imageVector = if (expanded) Icons.Rounded.Close else Icons.Rounded.Add,
            contentDescription = if (expanded) "Collapse" else "Expand",
            tint = Color.White,
            modifier = modifier
        )
    }
}


@Composable
fun CreateItemButton(action: () -> Unit, modifier: Modifier= Modifier){
    ExtendedFloatingActionButton(
        onClick = action,
        text = {Text("Create an Item")},
        modifier = modifier,
        icon = {Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Create Item",
            tint = Color.White,
            modifier = modifier
        )})
}
@Composable
fun AcceptButton(action: () -> Unit){
    IconButton(onClick = action) {
        Icon(imageVector = Icons.Filled.Done, contentDescription = "Accept")
    }
}

@Composable
fun RejectButton(action: () -> Unit){
    IconButton(onClick = action) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Reject")
    }
}
