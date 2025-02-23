package com.example.vktestvideoapplication.videoApi

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


interface VideoApiService {
    suspend fun getVideoData(samples : Int) : Videos

    companion object {
        fun create() : VideoApiService {
            return VideoApiServiceImpl(
                client = HttpClient(Android) {
                    install(ContentNegotiation) {
                        json(Json {
                            ignoreUnknownKeys = true }
                        )
                    }
                }
            )
        }
    }
}