package com.vibesoflove.ui.mix.item.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.db.DataBaseEntity
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
    private val savedMix = dataBaseRepository.getAll()
    val videoList =  currentData.combine(savedData){ baseModel, dataBase ->
        dataBase.map {
            MixModel(
                    id = it.id,
                    placeholder =  pixelRepository.findVideoById(it.id).image,
                    type = "Video"
            )
        }
    }.asLiveData()


    fun saveVideo(model: MixModel){
        viewModelScope.launch {
            savedMix.collect {
              if(it.isEmpty()){
                  Timber.i{
                      "Add"
                  }
              }
                    it.map {
                        if(it.id == model.id){
                            Timber.i{
                                "this element is not  saved"
                            }
                        }else{
                            Timber.i{
                                "this element whas saved"
                            }
                        }
                    }
            }
        }
    }
}