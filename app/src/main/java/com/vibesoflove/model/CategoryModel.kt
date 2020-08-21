package com.vibesoflove.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryModel(
        val name:String
):Parcelable{
    constructor():this("")
}