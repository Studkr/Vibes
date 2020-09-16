package com.vibesoflove.ui.mix.item.audio

import androidx.lifecycle.ViewModel
import com.vibesoflove.db.AudioEntity
import com.vibesoflove.repository.repository.DataBaseRepository
import javax.inject.Inject

class AudioItemViewModel @Inject constructor(
        private val dataBaseRepository: DataBaseRepository
): ViewModel() {

    val savedAudioList = dataBaseRepository.getAudioList()


    fun audioClicked(item : AudioEntity){

    }
}