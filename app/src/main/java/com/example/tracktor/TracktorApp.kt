package com.example.tracktor

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.tracktor.screens.login.LoginScreen
import com.example.tracktor.screens.pickingmode.PickingModeScreen
import com.example.tracktor.screens.selectfarm.SelectFarmScreen
import com.example.tracktor.screens.selectmode.SelectModeScreen
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
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
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
        PickingModeScreen()
    }

    composable(SELECT_FARM_SCREEN){
        SelectFarmScreen(
            openScreen = { route -> appState.navigate(route)}
        )
    }

}