package com.vibesoflove.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.R
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.ContentModel
import com.vibesoflove.repository.repository.PixelRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val pixelRepository:PixelRepository,
        private val firestore: FirebaseFirestore
) : ViewModel() {

    val content = MutableLiveData<List<ContentModel>>()
    val categoryList = MutableLiveData<List<CategoryModel>>()
    val openCategoryFragment = LiveEvent<CategoryModel>()

    init {
        viewModelScope.launch {
            getCategory()
        }
    }

    private fun getCategory(){
        firestore.collection("main").get().addOnSuccessListener {
            categoryList.value = it.toObjects(CategoryModel::class.java)
        }
    }

    fun openCategory(it: CategoryModel) {
        openCategoryFragment.value = it
    }
}

data class Content(
        val contentModel: ContentModel,
        val isChoose: Boolean
)


//
//    val baseList = arrayListOf<ContentModel>(
//            ContentModel(
//                    id = 0,
//                    audioFileName = "med_audio",
//                    videoFileName = "medita",
//                    roomName = "Meditation",
//                    placeholder = R.drawable.meditation
//            ),
//            ContentModel(
//                    id = 1,
//                    audioFileName = "med_audio",
//                    videoFileName = "medita",
//                    roomName = "Harmony",
//                    placeholder = R.drawable.harmony
//            ),
//            ContentModel(
//                    id = 2,
//                    audioFileName = "harmony",
//                    videoFileName = "harmon",
//                    roomName = "Night city",
//                    placeholder = R.drawable.night_city
//            ),
//            ContentModel(
//                    id = 3,
//                    audioFileName = "oc",
//                    videoFileName = "ocean",
//                    roomName = "Ocean",
//                    placeholder = R.drawable.ocean
//            ),
//            ContentModel(
//                    id = 4,
//                    audioFileName = "rain",
//                    videoFileName = "rain_v",
//                    roomName = "Rain",
//                    placeholder = R.drawable.rain
//            ),
//            ContentModel(
//                    id = 5,
//                    audioFileName = "romantic",
//                    videoFileName = "rom",
//                    roomName = "Romantic",
//                    placeholder = R.drawable.romantic
//            ),
//            ContentModel(
//                    id = 6,
//                    audioFileName = "starfall",
//                    videoFileName = "star",
//                    roomName = "Starfall",
//                    placeholder = R.drawable.white_noise
//            ),
//            ContentModel(
//                    id = 7,
//                    audioFileName = "immersion",
//                    videoFileName = "imm",
//                    roomName = "Immersion",
//                    placeholder = R.drawable.evolution
//            ),
//            ContentModel(
//                    id = 8,
//                    audioFileName = "fire",
//                    videoFileName = "fi",
//                    roomName = "Bonfire",
//                    placeholder = R.drawable.fire
//            ),
//            ContentModel(
//                    id = 9,
//                    audioFileName = "aquarium",
//                    videoFileName = "ac",
//                    roomName = "Aquarium",
//                    placeholder = R.drawable.aquarium
//            )
//    )
