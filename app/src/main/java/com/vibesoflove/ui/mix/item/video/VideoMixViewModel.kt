package com.vibesoflove.ui.mix.item.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vibesoflove.db.MixContainer
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.ui.mix.item.MixModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoMixViewModel @Inject constructor(
        private val dataBaseRepository: DataBaseRepository,
        private val pixelRepository: PixelRepository
): ViewModel() {
    private  val currentData = MutableStateFlow<List<MixModel>>(emptyList())
    private  val savedData = dataBaseRepository.getSavedVideo()
    private val mixContainer = dataBaseRepository.getContainer()
    val videoList =  currentData.combine(savedData){ baseModel, dataBase ->
        dataBase.map {
            MixVideo(
                    id = it.id,
                    placeholder =  pixelRepository.findVideoById(it.id).image,
                    type = "Video",
                    isChoose = false
            )
        }
    }.asLiveData()


    fun saveVideo(model: MixVideo){
        viewModelScope.launch {
            mixContainer.collect {

            }
        }
    }
}

data class MixVideo(
        val id: Long,
        val placeholder: String,
        val type: String,
        val isChoose: Boolean
)