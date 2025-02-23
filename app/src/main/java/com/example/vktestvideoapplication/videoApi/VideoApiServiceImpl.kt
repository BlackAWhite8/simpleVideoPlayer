package com.example.vktestvideoapplication.videoApi

import android.content.Context
import android.widget.Toast
import com.example.vktestvideoapplication.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.callContext
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import com.example.vktestvideoapplication.BuildConfig
class VideoApiServiceImpl(
    private val client : HttpClient,
) : VideoApiService {
    override suspend fun getVideoData(samples : Int) : Videos {
        val apiKey = BuildConfig.API_KEY
        return try {

            client.get(Routes.PEXEL_VIDEO_API.url) {
                url {
                    parameters.append("per_page", "$samples")
                }
                headers {
                    append(
                        HttpHeaders.Authorization, apiKey
                    )
                }
           }.body<Videos>()
        } catch (e : RedirectResponseException) {
            println("Error: ${e.response.status.description}")
            Videos(1, mutableListOf())
        } catch (e : ClientRequestException) {
            println("Error: ${e.response.status.description}")
            Videos(1, mutableListOf())
        } catch (e : ServerResponseException) {
            println("Error: ${e.response.status.description}, add: try to check connection. Maybe should use vpn?")
            Videos(1, mutableListOf())
        }

    }
}