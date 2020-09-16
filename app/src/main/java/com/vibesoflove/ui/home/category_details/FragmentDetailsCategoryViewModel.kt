package com.vibesoflove.ui.home.category_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.model.Video
import com.vibesoflove.model.VideoFile
import com.vibesoflove.model.VideoModel
import com.vibesoflove.repository.api.PixelsApi
import com.vibesoflove.repository.repository.PixelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentDetailsCategoryViewModel @Inject constructor(
        private val api: PixelRepository
) : ViewModel() {


    val videoList = MutableLiveData<VideoModel>()

    val openVideo = LiveEvent<VideoFile>()

    fun loadData(data: String) {
        viewModelScope.launch {
           // videoList.value = api.getVideoCategory(data)
        }
    }

    fun openVideo(it: Video) {
        openVideo.value = it.videoFiles.find { it.quality.name == "HD" }

    }

}