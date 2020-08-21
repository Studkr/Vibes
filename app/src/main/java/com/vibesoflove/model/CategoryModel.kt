package com.vibesoflove.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryModel(
        val name:String,
        val category:String
):Parcelable{
    constructor():this("","")
}