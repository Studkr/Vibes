package com.vibesoflove.ui.mix.item.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.db.MixContainer
import com.vibesoflove.db.VideoEntity
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.ui.mix.item.MixModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoMixViewModel @Inject constructor(
        private val dataBaseRepository: DataBaseRepository,
        private val pixelRepository: PixelRepository
) : ViewModel() {

    private val currentData = MutableStateFlow<List<MixModel>>(emptyList())

    private val savedData = dataBaseRepository.getSavedVideo()

    private val mixContainer = dataBaseRepository.getContainer()


//    val videoList =  currentData.combine(savedData){ baseModel, dataBase ->
//        dataBase.map {video ->
//            MixVideo(
//                    id = video.id,
//                    placeholder =  pixelRepository.findVideoById(video.id).image,
//                    type = "Video",
//                    isChoose = false
//            )
//        }
//    }.asLiveData()


    val videoList = savedData.combine(mixContainer) { savedData, mix ->
        if(mix.isEmpty()) {
            savedData.map { video ->
                MixVideo(
                        id = video.id,
                        placeholder = pixelRepository.findVideoById(video.id).image,
                        type = "video",
                        isChoose = false
                )
            }
        }else {
            savedData.map { video ->
                MixVideo(
                        id = video.id,
                        placeholder = pixelRepository.findVideoById(video.id).image,
                        type = "video",
                        isChoose = mix.first().videoId == video.id
                )
            }
        }
    }.asLiveData()


    fun saveVideo(model: MixVideo) {
        viewModelScope.launch {
            dataBaseRepository.getContainer().collect {
                if(it.isNotEmpty()){
                    dataBaseRepository.updateData(
                            MixContainer(
                                    id = 1,
                                    videoId = model.id,
                                    audioId = it.first().audioId,
                                    imageId = it.first().imageId
                            )
                    )
                }else{
                    dataBaseRepository.insertInContainer(
                            MixContainer(
                                    id = 1,
                                    videoId = model.id,
                                    audioId = 0,
                                    imageId = " "
                            )
                    )
                }
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