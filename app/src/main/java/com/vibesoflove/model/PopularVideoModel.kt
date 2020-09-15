package com.vibesoflove.model

import com.fasterxml.jackson.annotation.JsonProperty


data class PopularVideoModel (
        val page: Long,
        val perPage: Long,
        @JsonProperty("")
        val totalResults: Long,
        val url: String,
        val videos: List<VideoPopular>
)

data class VideoPopular (
        @JsonProperty("full_res")
        val fullRes: Any? = null,
        val tags: List<Any?>,
        val id: Long,
        val width: Long,
        val height: Long,
        val url: String,
        val image: String,
        val duration: Long,
        val user: User,
        @JsonProperty("video_files")
        val videoFiles: List<VideoFile>,
        @JsonProperty("video_pictures")
        val videoPictures: List<VideoPicturePopular>
)


data class VideoPicturePopular (
        val id: Long,
        val picture: String? = null,
        val nr: Long
)
