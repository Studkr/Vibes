package com.vibesoflove.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.db.DataBaseEntity
import com.vibesoflove.model.*
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.ui.mix.MyMixContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val pixelRepository: PixelRepository,
        private val firestore: FirebaseFirestore,
        private val dataBaseRepo: DataBaseRepository,
        private val audioPlayer: AudioPlayer
) : ViewModel() {

    val baseList = MutableStateFlow<List<AudioFirebaseModel>>(emptyList())
    val content = MutableLiveData<List<ContentModel>>()
    val categoryList = MutableLiveData<List<CategoryModel>>()
    val openVideoFragment = LiveEvent<VideoFile>()

    private val popularVideo = MutableStateFlow<List<VideoPopular>>(emptyList())
    private val popularPhoto = MutableStateFlow<List<Photo>>(emptyList())
    private val audioList = MutableLiveData<List<AudioFirebaseModel>>()

    val openAudioPlayer = LiveEvent<String>()
    val openMyMix = LiveEvent<String>()
    val openContent = LiveEvent<String>()

    val isPlaying = audioPlayer._isPlaying

    private val savedList = MutableStateFlow<List<DataBaseEntity>>(emptyList())

    init {
        viewModelScope.launch {
            getCategory()
            firestore.collection("music").get()
                    .addOnSuccessListener {
                        baseList.value = it.toObjects(AudioFirebaseModel::class.java)
                    }
            popularVideo.value = pixelRepository.getPopularVideo().videos
            popularPhoto.value = pixelRepository.getPopularPhoto().photos
            audioList.value = baseList.value
            dataBaseRepo.getAll().map {
                savedList.value = it
            }
        }
    }

    val contentCategory = popularVideo.combine(popularPhoto) { video, photo ->
        PopularContent(
                video,
                photo,
                baseList.value,
                savedList.value
        )
    }.asLiveData()

    private fun getCategory() {
        firestore.collection("main").get().addOnSuccessListener {
            categoryList.value = it.toObjects(CategoryModel::class.java)
        }
    }

    fun openCategory(it: VideoPopular) {
        viewModelScope.launch {
            it.videoFiles.map {
                if (it.quality.name == "HD") {
                    openVideoFragment.value = it
                }
            }
        }
    }


    fun openMyMixFragment(data: String) {
        when (data) {
            "My Mix" -> openMyMix.value = MyMixContainer::class.java.name
            "new audio" -> openAudioPlayer.value = data
            else -> {
                openContent.value = data
            }
        }
    }
}

data class PopularContent(
        val video: List<VideoPopular>,
        val photo: List<Photo>,
        val audio: List<AudioFirebaseModel>,
        val savedList: List<DataBaseEntity>
)

data class Content(
        val contentModel: ContentModel,
        val isChoose: Boolean
)


