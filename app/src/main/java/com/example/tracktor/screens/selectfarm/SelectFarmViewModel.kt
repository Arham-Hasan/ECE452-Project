package com.example.tracktor.screens.selectfarm
import com.example.tracktor.model.Farm
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectFarmViewModel @Inject constructor(
) : TracktorViewModel(){

    var farms : List<Farm> = listOf<Farm>()
    init{
        farms = retrieveFarms()
    }

    private fun retrieveFarms(): List<Farm> {
        // Simulated data retrieval
        return listOf(Farm(id="1", name = "Heeko farm"), Farm(id="1", name = "Boge farm"), Farm(id="1", name = "Arham farm"))
    }
}