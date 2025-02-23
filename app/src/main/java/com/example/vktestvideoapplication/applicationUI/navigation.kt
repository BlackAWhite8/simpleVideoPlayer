package com.example.vktestvideoapplication.applicationUI

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import kotlinx.serialization.Serializable

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreen) {
        composable<MainScreen> {
            MainScreen(navController)
        }

        composable<VideoScreenObject> { navBackStackEntry ->
            VideoScreen(navBackStackEntry)
        }
    }
}

@Serializable
object MainScreen

@Serializable
data class VideoScreenObject(
    val url : String
)