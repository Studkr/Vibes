package com.vibesoflove.model

import androidx.annotation.DrawableRes

data class ContentModel(
        val id: Int,
        val audioFileName: String,
        val videoFileName: String,
        @DrawableRes val placeholder:Int,
        val roomName : String
)