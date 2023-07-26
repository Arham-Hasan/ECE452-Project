package com.example.tracktor.screens.market

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.data.repository.InstagramPostRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    userManagerRepository: UserManagerRepository,
    private val instgramPostRepository: InstagramPostRepository
) : TracktorViewModel(userManagerRepository) {
    var uiState = mutableStateOf(MarketUiState())
        private set

    private val posts
        get() = uiState.value.posts

    private val fridge
        get() = uiState.value.fridge

    private val hashtagAlert
        get() = uiState.value.hashtagAlert

    fun setFridge(fridge: Fridge){
        uiState.value = uiState.value.copy(fridge = fridge)
        println(fridge.hashtag)
        launchCatching {
            uiState.value = uiState.value.copy(posts = instgramPostRepository.getInstagramPosts(fridge))

        }
    }
    fun toggleAlert(){
        uiState.value = uiState.value.copy(hashtagAlert = !hashtagAlert)
    }

}