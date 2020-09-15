package com.vibesoflove.model

data class AudioFirebaseModel(
        val id: Int,
        val name: String,
        val image: String,
        val link: String
) {
    constructor() : this(0,"", "", "")
}