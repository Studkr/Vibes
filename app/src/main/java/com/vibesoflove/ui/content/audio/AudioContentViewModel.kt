package com.vibesoflove.ui.content.audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.db.AudioEntity
import com.vibesoflove.model.AudioFirebaseModel
import com.vibesoflove.repository.repository.DataBaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AudioContentViewModel @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val dataBaseRepo: DataBaseRepository,
        private val audioPlayer: AudioPlayer
) : ViewModel() {

    val audioCategoriList = MutableStateFlow<List<AudioFirebaseModel>>(emptyList())
    private val savedAudioList = dataBaseRepo.getAudioList()
    val beginPlayAudio = LiveEvent<AudioListModule>()

    val isPlaying = audioPlayer._isPlaying.asLiveData()

    val currentPlaylist = audioCategoriList.combine(savedAudioList) { categoriList, savedList ->
        categoriList.map {
            AudioListModule(
                    id = it.id,
                    audio = it,
                    isSaved = savedList.contains(AudioEntity(id = it.id,audioName = it.name,link = it.link,audioImage = it.image))
            )
        }
    }

    init {
        firestore.collection("music").get().addOnSuccessListener {
            audioCategoriList.value = it.toObjects(AudioFirebaseModel::class.java)
        }
    }

    fun audioClicked(audioModel: AudioListModule) {
        audioCategoriList.value.mapIndexed {index, model ->
            if(model.id == audioModel.id){
                audioPlayer.playSelected(index)
            }
        }
    }



    fun saveAudioToFavorite(audioModel: AudioListModule) {
        viewModelScope.launch {
            if(audioModel.isSaved){
                dataBaseRepo.deleteAudio(AudioEntity(id = audioModel.id,audioName = audioModel.audio.name,link = audioModel.audio.link,audioImage = audioModel.audio.image))
            }else{
                dataBaseRepo.insertAudio(AudioEntity(id = audioModel.id,audioName = audioModel.audio.name,link = audioModel.audio.link,audioImage = audioModel.audio.image))
            }
        }

    }
}

data class AudioListModule(
        val id: Int,
        val audio: AudioFirebaseModel,
        val isSaved: Boolean
)