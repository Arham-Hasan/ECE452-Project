package com.example.tracktor.screens.market

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.common.Fridges.fridgeMapping
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.HashtagAlertDialog
import com.example.tracktor.common.composable.InstagramPostDay
import com.example.tracktor.data.model.InstagramPost
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MarketScreen(fridgeName: String,viewModel: MarketViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    viewModel.setFridge(fridgeMapping[fridgeName]!!)


    MarketScreenContent(
        uiState,
        { viewModel.toggleAlert() }
    )

}

@Composable
fun Photos(posts: List<Pair<String, List<InstagramPost>>>){
    posts.forEach {post ->
        Row{
            InstagramPostDay(post.first,post.second)
        }

    }

}

@Composable
fun FridgeMap(fridge: Fridge){
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition(fridge.latlng,15f,0f,0f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,

    ) {
        Marker(
            state = MarkerState(position = fridge.latlng),
            title = fridge.name,
            snippet = fridge.address,
        )
    }
}

@Composable
fun FridgeInfo (fridge: Fridge,toggle: ()->Unit){
    Text(
        text = "Information",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(15.dp)
    )
    Divider()
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(fridge.name)
            }
        },
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(15.dp)

    )
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Location: ")
            }
            append(fridge.address)
        },
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(15.dp)
    )
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Fridge Hashtag: ")
            }
            append(fridge.hashtag)
        },
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(15.dp)
    )
    ClickableText(
        modifier = Modifier.padding(top =10.dp).padding(15.dp),
        text= AnnotatedString("How to add images to fridge information?"),
        onClick = {
            toggle()
        },
        style = TextStyle(textDecoration = TextDecoration.Underline, color = Color.Blue),
    )


}


@Composable
fun MarketScreenContent(
    uiState: MarketUiState,
    toggle: ()->Unit
)
{
    val state = rememberScrollState()
    HashtagAlertDialog(toggleAlert = toggle, AlertVisible = uiState.hashtagAlert)
    Column(
        modifier = Modifier
            .verticalScroll(state)
    )
    {

        BasicToolbar(title = uiState.fridge.name)

        FridgeInfo(uiState.fridge,toggle)
        Row(){
            Column(modifier = Modifier
                .fillMaxWidth(.75f)
                .padding(15.dp)
                .aspectRatio(1f)){
                FridgeMap(uiState.fridge)
            }

        }
        Row(){
            Text(
                text = "Recent Pictures",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(15.dp)
            )
        }
        Divider()


        Photos(uiState.posts)

    }

}

//@Composable
//@Preview
//fun MarketScreenContentPreview(){
//    TracktorTheme {
//        MarketScreenContent(MarketUiState())
//    }
//}