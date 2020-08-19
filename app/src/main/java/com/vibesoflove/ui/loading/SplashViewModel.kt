package com.vibesoflove.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    val launchMainScreen = LiveEvent<Unit>()

    init {

        viewModelScope.launch {
            delay(3000)
            launchMainScreen.value = Unit

        }
    }
}