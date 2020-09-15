package com.vibesoflove.model

import com.fasterxml.jackson.annotation.JsonProperty

data class PopularPhoto (
        val page: Long,
        @JsonProperty("per_page")
        val perPage: Long,
        val photos: List<Photo>,
        @JsonProperty("next_page")
        val nextPage: String
)

data class Photo (
        val id: Long,
        val width: Long,
        val height: Long,
        val url: String,
        val photographer: String,
        @JsonProperty("photographer_url")
        val photographerURL: String,
        @JsonProperty("photographer_id")
        val photographerID: Long,
        val src: Src,
        val liked: Boolean
)

data class Src (
        val original: String,
        @JsonProperty("large2x")
        val large2X: String,
        val large: String,
        val medium: String,
        val small: String,
        val portrait: String,
        val landscape: String,
        val tiny: String
)
