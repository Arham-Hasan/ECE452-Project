package com.example.tracktor.common.composable

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.R
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun NavBarComposable(
    currentScreen: String,
    pickingAction: ()->Unit,
    sellingAction: ()->Unit,
    marketAction: ()->Unit,
    analyticsAction: ()->Unit,
    settingsAction: ()->Unit,
    modifier: Modifier = Modifier
){
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.MyNetwork,
        BottomNavItem.AddPost,
        BottomNavItem.Notification,
        BottomNavItem.Jobs
    ) zip listOf(pickingAction,sellingAction,marketAction,analyticsAction,settingsAction)
    BottomNavigation(contentColor = Color.Black) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.first.icon), contentDescription = item.first.title) },
                label = { Text(text = item.first.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentScreen == item.first.screen_name,
                onClick = item.second
            )
        }
    }
}
@Preview
@Composable
fun NavBarComposablePreview(){
    TracktorTheme {
        NavBarComposable(
            "PickingModeScreen",
            {},{},{},{},{}
        )
    }
}


sealed class BottomNavItem(var title:String, var icon:Int, var screen_name:String){

    object Home : BottomNavItem("Picking", R.drawable.baseline_agriculture_24, PICKING_MODE_SCREEN)
    object MyNetwork: BottomNavItem("Selling",R.drawable.baseline_point_of_sale_24,
        SELLING_MODE_SCREEN)
    object AddPost: BottomNavItem("Fridges",R.drawable.food_bank, FRIDGE_MODE_SCREEN)
    object Notification: BottomNavItem("Analytics",R.drawable.baseline_bar_chart_24,
        ANALYTICS_MODE_SCREEN)
    object Jobs: BottomNavItem("Inventory",R.drawable.baseline_inventory_24, INVENTORY_MODE_SCREEN)
}