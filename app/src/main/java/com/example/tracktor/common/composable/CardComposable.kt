package com.example.tracktor.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun AnalyticsCard(
    text: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text= text
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCard(
    imageBitmap: ImageBitmap,
    price: Double,
    name: String,
    quantity: Long,
    action: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        onClick = action
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Name: $name")
                Text(text = "Price: \$$price")
                Text(text = "Quantity: $quantity")
            }
        }
    }
}

