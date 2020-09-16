package com.vibesoflove.ui.content

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.vibesoflove.model.CategoryFirebaseModel
import javax.inject.Inject

class CategoryContainerViewModel @Inject constructor(
        private val firestore: FirebaseFirestore
) : ViewModel() {

    val firebaseCategory = MutableLiveData<List<CategoryFirebaseModel>>()

    init {
        firestore.collection("relax").get().addOnSuccessListener {
            firebaseCategory.value = it.toObjects(CategoryFirebaseModel::class.java)
        }
    }
}