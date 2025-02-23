package com.example.vktestvideoapplication.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import com.example.vktestvideoapplication.videoApi.VideoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VideoPlayerModule {
    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(app: Application) : Player {
        return ExoPlayer.Builder(app).build()
    }

    @Provides
    @ViewModelScoped
    fun provideVideoApiService() : VideoApiService {
        return VideoApiService.create()
    }
}