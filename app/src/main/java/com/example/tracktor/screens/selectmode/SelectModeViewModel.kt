package com.example.tracktor.screens.selectmode

import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectModeViewModel @Inject constructor() : TracktorViewModel(){
    fun onPickingModeClick(navigate: (String) -> Unit){
        navigate(PICKING_MODE_SCREEN)
    }

}