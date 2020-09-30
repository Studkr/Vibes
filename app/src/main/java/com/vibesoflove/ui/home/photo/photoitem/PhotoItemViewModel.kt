package com.vibesoflove.ui.home.photo.photoitem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.model.Photo
import com.vibesoflove.repository.repository.PixelRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoItemViewModel @Inject constructor(
        private val repository: PixelRepository
): ViewModel() {
    val choosePhoto = MutableLiveData<Photo>()
    val openInfo = LiveEvent<String>()

    fun loadPhotoById(photo:Long){
        viewModelScope.launch {
            choosePhoto.value =  repository.findPhotoById(photo)
        }
    }

    fun showInfo() {
        openInfo.value = choosePhoto.value?.photographerURL
    }
}