package com.example.tracktor.screens.fridgemode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.common.composable.FridgeAlertDialog
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.reflect.KFunction2

@Composable
fun FridgeModeScreen(openScreen: (String)->Unit,clearAndNavigate:(String)->Unit, viewModel: FridgeModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    FridgeModeScreenContent(
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate),
        uiState.fridges,
        viewModel::onMarkerClick,
        openScreen,
        {viewModel.toggleAlert()}
    )

}

@Composable
fun FridgeMap(fridges: List<Fridge>, onMarkerClick: KFunction2<(String) -> Unit, String, Unit>, openScreen: (String)->Unit, toggleAlert:()->Unit){
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition(LatLng(43.4477659,-80.4862877),15f,0f,0f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,

        ) {
            fridges.forEach { fridge -> Marker(
                state = MarkerState(position = fridge.latlng),
                title = fridge.name,
                snippet = fridge.address,
                onInfoWindowClick = {
                    toggleAlert()
                }
            )
//            ){
//                Button(onClick = { onMarkerClick(openScreen,fridge.name) }) {
//
//                }
        }

    }
}

@Composable
fun FridgeModeScreenContent(
    bottomNavActions:List<()->Unit>,
    uiState:FridgeModeUiState,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>,
    fridges: List<Fridge>,
    onMarkerClick: KFunction2<(String) -> Unit, String, Unit>,
    openScreen: (String)->Unit,
    toggleAlert: () -> Unit
)
{

    FridgeAlertDialog(toggleAlert,uiState.mapAlert, fridge = fridges[0],onMarkerClick, openScreen)
    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "View local fridges!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(Modifier.weight(1f)){
            Column(
                Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                    .fillMaxWidth(), verticalArrangement = Arrangement.Bottom){

                Box(modifier = Modifier
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()){
                    FridgeMap(fridges,onMarkerClick,openScreen,toggleAlert)
                }
                Row(){Column(){
                    Text(
                        modifier = Modifier.padding(top =15.dp),
                        text="New fridge or fridge got relocated?\nContact us at tracktorapp452@gmail.com",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }


                }

            }

        }
        NavBarComposable(FRIDGE_MODE_SCREEN, bottomNavActions)

    }
}

//@Composable
//@Preview
//fun FridgeModeScreenContentPreview(){
//    TracktorTheme {
//        FridgeModeScreenContent(listOf({}),FridgeModeUiState(),{},listOf(Pair("",{})), listOf())
//    }
//}