package com.example.vktestvideoapplication

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.example.vktestvideoapplication.videoApi.VideoApiService
import com.example.vktestvideoapplication.videoApi.VideoItem
import com.example.vktestvideoapplication.videoApi.Videos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val api: VideoApiService
)  : ViewModel() {
    var isLoad = mutableStateOf(false)
    var videos = mutableStateOf(Videos(1, mutableListOf()))
    private val videoUrls = savedStateHandle.getStateFlow("urls", HashMap<String,Uri>())
    private var cachedVideos = videoUrls.map { store ->
        store.map { f ->
            VideoMediaItem(
                uri = f.value,
                url = f.key,
                mediaItem = MediaItem.fromUri(f.value)
            )
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10000), emptyList())

    init {
        player.prepare()
    }

    suspend fun loadDataFromApi(samples : Int = 5) {
        viewModelScope.launch {
            isLoad.value = true
            videos.value = api.getVideoData(samples)

        }
    }



    private fun isCachedVideo(url: String) : MediaItem? {
        val isCached = cachedVideos.value.find { url == it.url }?.mediaItem
        return isCached
    }

    private fun saveVideo(item : VideoMediaItem) {
        savedStateHandle["urls"] = videoUrls.value.plus(Pair(item.url, item.uri))
    }

    fun playVideo(url: String) {
        val item = isCachedVideo(url)
        if(item != null) {
            player.setMediaItem(item)
            player.play()
        } else {
            val uri = Uri.parse(url)
            val newVideoItem = VideoMediaItem(
                uri = uri,
                url = url,
                mediaItem = MediaItem.fromUri(uri)
            )
            saveVideo(newVideoItem)
            player.setMediaItem(newVideoItem.mediaItem)
            player.play()
        }

    }


    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}