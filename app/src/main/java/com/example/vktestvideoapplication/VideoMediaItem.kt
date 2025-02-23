package com.example.vktestvideoapplication

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoMediaItem(
    val uri : Uri,
    val url : String,
    val mediaItem: MediaItem
)
