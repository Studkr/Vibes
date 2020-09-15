package com.vibesoflove.ui.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.vibesoflove.db.PhotoEntity
import com.vibesoflove.db.VideoDao
import com.vibesoflove.db.VideoEntity
import com.vibesoflove.model.Photo
import com.vibesoflove.model.VideoPopular
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContentViewModel @Inject constructor(
        private val pixelRepository: PixelRepository,
        private val firestore: FirebaseFirestore,
        private val dataBaseRepo: DataBaseRepository
) : ViewModel() {

    private val popularVideo = MutableStateFlow<List<VideoPopular>>(emptyList())
    private val savedVideo = dataBaseRepo.getSavedVideo()
    private val popularPhoto = MutableStateFlow<List<Photo>>(emptyList())
    private val savedPhoto = dataBaseRepo.getSavedPhoto()

    val currentPopularVideo = popularVideo.combine(savedVideo) { video, savedVideo ->
        video.map {
            ContentModel(
                    id = it.id,
                    placeholder = it.image,
                    isSaved = savedVideo.contains(VideoEntity(it.id)),
                    type = "Video"
            )
        }
    }.asLiveData()

     val currentPopularPhoto = popularPhoto.combine(savedPhoto){popular,saved ->
         popular.map {
             ContentModel(
                     id = it.id,
                     placeholder = it.src.large ,
                     isSaved = saved.contains(PhotoEntity(it.id)),
                     type = "Photo"
             )
         }
     }.asLiveData()


    fun loadData(data: String) {
        when (data) {
            "new video" -> {
                loadVideo()
            }
            "new photo" -> {
                loadPhoto()
            }
        }
    }

    private fun loadPhoto() {
        viewModelScope.launch {
            popularPhoto.value = pixelRepository.getPopularPhoto().photos
        }
    }


    fun loadVideo() {
        viewModelScope.launch {
            popularVideo.value = pixelRepository.getPopularVideo().videos

        }
    }

    fun savedVideo(model: ContentModel) {
        viewModelScope.launch {
            when(model.type){
                "Video" -> {
                    if (currentPopularVideo.value?.find { it.placeholder == model.placeholder }?.isSaved!!) {
                        dataBaseRepo.deleteVideo(VideoEntity(model.id))
                    } else {
                        dataBaseRepo.insertVideo(VideoEntity(model.id))
                    }
                }
                "Photo"->{
                    if (currentPopularPhoto.value?.find { it.placeholder == model.placeholder }?.isSaved!!) {
                        dataBaseRepo.deletePhoto(PhotoEntity(model.id))
                    } else {
                        dataBaseRepo.insertPhoto(PhotoEntity(model.id))
                    }
                }


            }

        }
    }
}


data class ContentModel(
        val id: Long,
        val placeholder: String,
        val isSaved: Boolean,
        val type: String
)