package com.example.tracktor.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

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