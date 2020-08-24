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


