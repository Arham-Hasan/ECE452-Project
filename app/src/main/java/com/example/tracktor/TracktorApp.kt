package com.example.tracktor

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.screens.analyticsmode.AnalyticsModeScreen
import com.example.tracktor.screens.createfarm.CreateFarmScreen
import com.example.tracktor.screens.createitem.CreateItemScreen
import com.example.tracktor.screens.farmsettings.FarmSettingsScreen
import com.example.tracktor.screens.fridgemode.FridgeModeScreen
import com.example.tracktor.screens.inventorymode.InventoryModeScreen
import com.example.tracktor.screens.login.LoginScreen
import com.example.tracktor.screens.managemembers.ManageMembersScreen
import com.example.tracktor.screens.pickingmode.PickingModeScreen
import com.example.tracktor.screens.selectfarm.SelectFarmScreen
import com.example.tracktor.screens.selectmode.SelectModeScreen
import com.example.tracktor.screens.sellingmode.SellingModeScreen
import com.example.tracktor.screens.signup.SignUpScreen
import com.example.tracktor.screens.splash.SplashScreen
import com.example.tracktor.ui.theme.TracktorTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun TracktorApp(){
    TracktorTheme {

        val appState = rememberAppState()

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = it,
                    modifier = Modifier.padding(8.dp),
                    snackbar = { snackbarData ->
                        Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                    }
                )
            },
            scaffoldState = appState.scaffoldState
        ){contentPadding ->
            NavHost(
                navController = appState.navController,
                startDestination = SPLASH_SCREEN,
                modifier = Modifier.padding(contentPadding)
            ){
                TracktorGraph(appState)
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackbarManager: SnackbarManager = SnackbarManager
)=
    remember(scaffoldState, navController,  resources, coroutineScope,snackbarManager){
        TracktorAppState(scaffoldState, navController, resources, coroutineScope,snackbarManager)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}


fun NavGraphBuilder.TracktorGraph(appState: TracktorAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(LOGIN_SCREEN){
        LoginScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            openScreen = { route -> appState.navigate(route)}
        )
    }

    composable(SIGN_UP_SCREEN){
        SignUpScreen(
            clearAndNavigate = { route -> appState.clearAndNavigate(route) },
            popUp = {appState.popUp()}
        )
    }

    composable(route="$SELECT_MODE_SCREEN$FARM_ID_ARG", arguments = listOf(navArgument(FARM_ID) { defaultValue = "" })){
        SelectModeScreen(
            navigate = {route -> appState.navigate(route)},
            farmID = it.arguments?.getString(FARM_ID) ?: ""
        )
    }

    composable(PICKING_MODE_SCREEN){
        PickingModeScreen(
            {route -> appState.navigate(route)},
            {route -> appState.clearAndNavigate(route)}
        )
    }
    composable(SELLING_MODE_SCREEN){
        SellingModeScreen(
            {route -> appState.navigate(route)},
            {route -> appState.clearAndNavigate(route)}
        )
    }
    composable(FRIDGE_MODE_SCREEN){
        FridgeModeScreen(
            {route -> appState.navigate(route)},
            {route -> appState.clearAndNavigate(route)}
        )
    }
    composable(ANALYTICS_MODE_SCREEN){
        AnalyticsModeScreen(
            {route -> appState.navigate(route)},
            {route -> appState.clearAndNavigate(route)}
        )
    }
    composable(INVENTORY_MODE_SCREEN){
        InventoryModeScreen(
            { route -> appState.navigate(route) },
            {route -> appState.clearAndNavigate(route)}
        )
    }


    composable(SELECT_FARM_SCREEN){
        SelectFarmScreen(
            openScreen = { route -> appState.navigate(route)},
            {route -> appState.clearAndNavigate(route)}
        )
    }

    composable(FARM_SETTINGS_SCREEN){
        FarmSettingsScreen(
            { route -> appState.navigate(route)},
            {route -> appState.clearAndNavigate(route)}
        )
    }
    
    composable(CREATE_FARM_SCREEN){
        CreateFarmScreen(
            openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)}
        )
    }

    composable(CREATE_ITEM_SCREEN){
        CreateItemScreen(
            openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)}
        )
    }

    composable(MANAGE_MEMBERS_SCREEN){
        ManageMembersScreen(
            openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)}
        )
    }

}