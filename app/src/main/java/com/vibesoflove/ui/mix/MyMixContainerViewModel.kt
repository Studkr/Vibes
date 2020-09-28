package com.vibesoflove.ui.mix

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.db.MixContainer
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyMixContainerViewModel @Inject constructor(
        private val dataBaseRepo: DataBaseRepository,
        private val pixelRepo:PixelRepository
): ViewModel() {

    val mix = dataBaseRepo.getContainer()
    val mixImage = LiveEvent<String>()

    fun createMix(model: MixContainer) {
        viewModelScope.launch {
            mixImage.value = pixelRepo.findVideoById(model.videoId).image
        }
    }
}