package com.example.vktestvideoapplication

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.Bitmap
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.bitmapFactoryMaxParallelism
import coil3.request.bitmapConfig
import coil3.request.maxBitmapSize
import coil3.size.Dimension
import coil3.size.Precision
import com.example.vktestvideoapplication.applicationUI.MainScreen
import com.example.vktestvideoapplication.applicationUI.Navigation
import com.example.vktestvideoapplication.ui.theme.VkTestVideoApplicationTheme
import com.example.vktestvideoapplication.videoApi.VideoApiService
import com.example.vktestvideoapplication.videoApi.VideoItem
import com.example.vktestvideoapplication.videoApi.Videos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }

}

