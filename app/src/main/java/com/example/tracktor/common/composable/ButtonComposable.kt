package com.example.tracktor.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextButton(text:String, modifier: Modifier, action: () -> Unit) {
    TextButton(
        onClick = action,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) { Text(text = text, color = Color(0xFF2F6F3D)) }
}

@Composable
fun BasicButton(text: String, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF2F6F3D),
            contentColor = Color.White // Changed here
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.White // Changed here
        )
    }
}

@Composable
fun MicButton(text:String, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF2F6F3D),
            contentColor = Color.White
        )
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.White)
    }
}
@Composable
fun CreateFarmButton(action: () -> Unit, modifier: Modifier= Modifier){
    ExtendedFloatingActionButton(
        onClick = action,
        text = {Text("Create a Farm", color = Color.White)},
        modifier = modifier.padding(8.dp),
        backgroundColor = Color(0xFF2F6F3D)
    )
}

@Composable
fun JoinFarmButton(action: () -> Unit, modifier: Modifier= Modifier){
    ExtendedFloatingActionButton(
        onClick = action,
        text = {Text("Join a Farm", color = Color.White)},
        modifier = modifier.padding(8.dp),
        backgroundColor = Color(0xFF2F6F3D)
    )
}

@Composable
fun ExpandableButton(action: () -> Unit, modifier: Modifier= Modifier, expanded: Boolean){
    FloatingActionButton(
        onClick = action,
        modifier = modifier.padding(8.dp).size(48.dp),
        containerColor = Color(0xFF2F6F3D),
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
        text = {Text("Create an Item", color = Color.White)},
        modifier = modifier,
        icon = {Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Create Item",
            tint = Color.White,
            modifier = modifier
        )},
        backgroundColor = Color(0xFF2F6F3D)
    )
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
