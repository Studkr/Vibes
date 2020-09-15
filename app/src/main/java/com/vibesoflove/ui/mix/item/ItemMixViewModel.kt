package com.vibesoflove.ui.mix.item

import androidx.constraintlayout.widget.Placeholder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.db.VideoEntity
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.ui.home.adapter.MyMixModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemMixViewModel @Inject constructor(
        private val dataBaseRepository: DataBaseRepository,
        private val pixelRepository: PixelRepository
) : ViewModel() {


  private  val currentData = MutableStateFlow<List<MixModel>>(emptyList())
  private  val savedData = dataBaseRepository.getSavedVideo()

    val visibleData =  currentData.combine(savedData){ baseModel, dataBase ->
        dataBase.map {
            MixModel(
                    id = it.id,
                    placeholder =  pixelRepository.findVideoById(it.id).image,
                    type = "Video"
            )
        }
    }.asLiveData()

    fun setItem(itemName: String) {
        when (itemName) {
            "My Mix" -> {
                loadMyMix()
            }
            "Video" -> {
                loadVideo()
            }
            "Music" -> {
                loadMusic()
            }
            "Photo" -> {
                loadPhoto()
            }
        }
    }

    private fun loadPhoto() {

    }

    private fun loadMusic() {

    }

    private fun loadVideo() {
        viewModelScope.launch {

        }
    }

    private fun loadMyMix() {

    }
}

data class MixModel(
        val id:Long,
        val placeholder:String,
        val type: String
)