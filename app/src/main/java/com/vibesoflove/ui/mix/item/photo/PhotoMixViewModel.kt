package com.vibesoflove.ui.mix.item.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.ui.mix.item.MixModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class PhotoMixViewModel @Inject constructor(
        private val dataBaseRepository: DataBaseRepository,
        private val pixelRepository: PixelRepository
): ViewModel() {

    private  val currentData = MutableStateFlow<List<MixModel>>(emptyList())
    private  val savedData = dataBaseRepository.getSavedPhoto()

    val videoList =  currentData.combine(savedData){ baseModel, dataBase ->
        dataBase.map {
            MixModel(
                    id = it.id,
                    placeholder =  pixelRepository.findPhotoById(it.id).src.large,
                    type = "Photo"
            )
        }
    }.asLiveData()
}