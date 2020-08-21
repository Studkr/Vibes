package com.vibesoflove.ui.home.deatils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.hadilq.liveevent.LiveEvent
import com.vibesoflove.model.CategoryModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    val category = MutableLiveData<List<CategoryModel>>()

    val openCategory = LiveEvent<String>()

    fun loadCategory(model: CategoryModel) {
            viewModelScope.launch {
                firestore.collection(model.category).get().addOnSuccessListener {
                    category.value = it.toObjects(CategoryModel::class.java)
                }
            }
    }

    fun chooseCategory(name:String){
        openCategory.value = name
    }
}