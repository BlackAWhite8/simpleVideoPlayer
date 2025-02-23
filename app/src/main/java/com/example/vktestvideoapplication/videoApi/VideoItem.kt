package com.example.vktestvideoapplication.videoApi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    val page : Int,
    val videos: MutableList<VideoItem>
)

@Serializable
data class VideoItem(
    val url: String,
    val image : String,
    val duration : Int,
    @SerialName("video_files")
    val videoFiles : List<VideoFiles>
)

@Serializable
data class VideoFiles(
    val id : Int,
    val link : String
)
