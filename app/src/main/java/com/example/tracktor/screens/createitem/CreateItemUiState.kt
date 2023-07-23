package com.example.tracktor.screens.createitem

import android.net.Uri

data class CreateItemUiState(
    val name: String = "",
    val price: String = "",
    var itemImage: Uri = Uri.EMPTY
)