package com.vibesoflove.ui.audio

import androidx.annotation.DrawableRes
import androidx.lifecycle.*
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.github.ajalt.timberkt.Timber
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.R
import com.vibesoflove.model.ContentModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentAudioPlayerViewModel @Inject constructor(
        private val audioPlayer: AudioPlayer
) : ViewModel() {

    val baseList = arrayListOf(
            ContentModel(
                    id = 0,
                    audioFileName = "med_audio",
                    videoFileName = "medita",
                    roomName = "Meditation",
                    placeholder = R.drawable.meditation
            ),
            ContentModel(
                    id = 1,
                    audioFileName = "harmony",
                    videoFileName = "harmon",
                    roomName = "Night city",
                    placeholder = R.drawable.night_city
            ),
            ContentModel(
                    id = 3,
                    audioFileName = "oc",
                    videoFileName = "ocean",
                    roomName = "Ocean",
                    placeholder = R.drawable.ocean
            ),
            ContentModel(
                    id = 4,
                    audioFileName = "rain",
                    videoFileName = "rain_v",
                    roomName = "Rain",
                    placeholder = R.drawable.rain
            ),
            ContentModel(
                    id = 5,
                    audioFileName = "romantic",
                    videoFileName = "rom",
                    roomName = "Romantic",
                    placeholder = R.drawable.romantic
            ),
            ContentModel(
                    id = 6,
                    audioFileName = "starfall",
                    videoFileName = "star",
                    roomName = "Starfall",
                    placeholder = R.drawable.white_noise
            ),
            ContentModel(
                    id = 7,
                    audioFileName = "immersion",
                    videoFileName = "imm",
                    roomName = "Immersion",
                    placeholder = R.drawable.evolution
            ),
            ContentModel(
                    id = 8,
                    audioFileName = "fire",
                    videoFileName = "fi",
                    roomName = "Bonfire",
                    placeholder = R.drawable.fire
            ),
            ContentModel(
                    id = 9,
                    audioFileName = "aquarium",
                    videoFileName = "ac",
                    roomName = "Aquarium",
                    placeholder = R.drawable.aquarium
            )
    )

    val curentAudioList = MutableLiveData<List<ContentModel>>()

    init {
        viewModelScope.launch {
            curentAudioList.value = baseList
        }
    }

    val flowList = MutableStateFlow(baseList)

    val _currentStatePlaySong =  flowList.combine(audioPlayer.isPlayerChange) { list, str ->
        if (str.isEmpty()) {
            list.mapIndexed { index, s ->
                CurrentPlayList(
                        id = index,
                        songName = s.audioFileName,
                        video = s.videoFileName,
                        placeholder = s.placeholder,
                        isPlaying = false
                )
            }
        } else {
            list.mapIndexed { index, s ->
                CurrentPlayList(
                        id = index,
                        songName = s.audioFileName,
                        video = s.videoFileName,
                        placeholder = s.placeholder,
                        isPlaying = str == s.audioFileName
                )
            }

        }
    }
    val currentSong = audioPlayer.isPlayerChange.asLiveData()
    val currentPlayList = _currentStatePlaySong.asLiveData()//MutableLiveData<List<CurrentPlayList>>()
    val isPlaying = audioPlayer._isPlaying.asLiveData()


    fun playListClicked(model :CurrentPlayList){
        currentPlayList.value?.mapIndexed { index, currentPlayList ->
            if(currentPlayList == model){
                audioPlayer.playSelected(index)
            }
        }
    }

    fun fullScreenPresed() {

    }
}

data class CurrentPlayList(
        val id: Int,
        val songName: String,
        val video:String,
        @DrawableRes val placeholder:Int,
        val isPlaying: Boolean
)