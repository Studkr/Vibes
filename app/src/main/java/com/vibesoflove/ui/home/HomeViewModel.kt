package com.vibesoflove.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.db.DataBaseEntity
import com.vibesoflove.model.*
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.ui.mix.MyMixContainer
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val pixelRepository: PixelRepository,
        private val firestore: FirebaseFirestore,
        private val dataBaseRepo: DataBaseRepository,
        private val audioPlayer: AudioPlayer
) : ViewModel() {

    val baseList = MutableStateFlow<List<AudioFirebaseModel>>(emptyList())
    val content = MutableLiveData<List<ContentModel>>()
    private val categoryList = MutableLiveData<List<CategoryModel>>()
    val openVideoFragment = LiveEvent<VideoFile>()

    private val popularVideo = MutableStateFlow<List<VideoPopular>>(emptyList())
    private val popularPhoto = MutableStateFlow<List<Photo>>(emptyList())
    private val audioList = MutableLiveData<List<AudioFirebaseModel>>()

    val openAudioPlayer = LiveEvent<String>()
    val openMyMix = LiveEvent<String>()
    val openContent = LiveEvent<String>()

    val isPlaying = audioPlayer._isPlaying

    private val savedList = MutableStateFlow<List<DataBaseEntity>>(emptyList())

    val errorMessage = LiveEvent<String>()

    init {
        updateData()
    }


    fun updateData(){
        viewModelScope.launch {
            getCategory()
            try {
                popularVideo.value = pixelRepository.getPopularVideo().videos
                popularPhoto.value = pixelRepository.getPopularPhoto().photos
            }catch (e:Exception){
                if(e.message?.contains("No address associated with hostname") == true){
                    errorMessage.value = "Check internet"
                }else{
                    errorMessage.value = "Please check internet? and reran application"
                }
            }

            firestore.collection("music").get()
                    .addOnSuccessListener {
                        baseList.value = it.toObjects(AudioFirebaseModel::class.java)
                    }


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
                        savedList.value,
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

