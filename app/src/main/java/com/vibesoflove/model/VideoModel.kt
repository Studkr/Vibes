package com.vibesoflove.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

data class VideoModel (
        val page: Long,
        @JsonProperty("per_page")
        val perPage: Long,
        @JsonProperty("total_results")
        val totalResults: Long,
        val url: String,
        val videos: List<Video>
)


data class Video (
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
        val videoPictures: List<VideoPicture?>
)
@Parcelize
data class User (
        val id: Long,
        val name: String,
        val url: String
):Parcelable
@Parcelize
data class VideoFile (
        val id: Long,
        val quality: Quality,
        @JsonProperty("file_type")
        val fileType: FileType,
        val width: Long? = null,
        val height: Long? = null,
        val link: String
):Parcelable

enum class FileType {
    @JsonProperty("video/mp4")
    VideoMp4
}

enum class Quality {
    @JsonProperty("hd")
    HD,
    @JsonProperty("hls")
    HLS,
    @JsonProperty("sd")
    SD,
    @JsonProperty("mobile")
    MOBILE
}
@Parcelize
data class VideoPicture (
        val id: Long?,
        val picture: String?,
        val nr: Long?
):Parcelable
