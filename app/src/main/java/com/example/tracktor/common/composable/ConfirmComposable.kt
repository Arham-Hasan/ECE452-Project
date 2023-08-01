package com.example.tracktor.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import com.example.tracktor.common.Fridges.Fridge
import kotlin.reflect.KFunction2

@Composable
fun AlertDialogConfirm(onConfirmClick:()->Unit, onDeclineClick:()->Unit, toggleAlert:()->Unit, AlertVisible:Boolean, mainText:String, secondText:String) {

            if (AlertVisible) {

                AlertDialog(
                    onDismissRequest =
                        toggleAlert,
                    title = {
                        Text(text = mainText)
                    },
                    text = {
                        Text(secondText)
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                toggleAlert()
                                onConfirmClick()
                            }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = toggleAlert
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }


}

@Composable
fun HashtagAlertDialog(toggleAlert:()->Unit, AlertVisible:Boolean){
    if (AlertVisible) {

        AlertDialog(
            onDismissRequest =
            toggleAlert,
            title = {
                Text(text = "Using Fridge Hashtags")
            },
            text = {
                Text("Photos from Instagram posts using #tracktorapp and the designated fridge hashtag (see the fridge info page), will help show an up to date state of the fridge.\nHelp everyone out and post today!\nThe images are built on a trust system, please keep images relevant and up to date.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        toggleAlert()
                    }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun FridgeAlertDialog(toggleAlert:(Fridge)->Unit, AlertVisible:Boolean, fridge: Fridge, onMarkerClick: KFunction2<(String) -> Unit, String, Unit>,
                      openScreen: (String)->Unit,){
    if (AlertVisible) {

        AlertDialog(
            onDismissRequest =
            { toggleAlert(fridge) },
            title = {
                Text(text = fridge.name)
            },
            text = {
                Text(fridge.address)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        toggleAlert(fridge)
                        onMarkerClick(openScreen,fridge.name)
                    }) {
                    Text("View pictures and more info")
                }
            }
        )
    }
}

@Composable
fun AlertTextConfirm(
    onConfirmClick:()->Unit,
    onDeclineClick:()->Unit,
    toggleAlert:()->Unit,
    AlertVisible:Boolean,
    mainText:String,
    textFieldPlaceholder:String,
    value: String,
    onNewValue: (String) -> Unit,
    label:String,
) {
    Column {

        if (AlertVisible) {

            AlertDialog(
                onDismissRequest =
                toggleAlert,
                title = {
                    Text(text = mainText)
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ){
                        BasicTextField(text = textFieldPlaceholder, value = value, onNewValue = onNewValue, label=label)
                    }
                },

                confirmButton = {
                    Button(

                        onClick = {
                            toggleAlert()
                            onConfirmClick()
                        }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    Button(

                        onClick = toggleAlert
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

}