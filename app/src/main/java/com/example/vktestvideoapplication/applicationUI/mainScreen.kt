package com.example.vktestvideoapplication.applicationUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.example.vktestvideoapplication.VideoPlayerViewModel
import com.example.vktestvideoapplication.videoApi.VideoItem
import com.example.vktestvideoapplication.videoApi.Videos
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {

        var loading = viewModel.isLoad.value
        val pullToRefreshState = rememberPullToRefreshState()
        if (!loading) {
            LaunchedEffect(Unit) {
                val videoSamples = (5..8).random()
                viewModel.loadDataFromApi(videoSamples)
                loading = true
            }
        }
        val videos = viewModel.videos.value
        Box(modifier = Modifier.fillMaxSize()) {
            PullToRefreshBox(
                onRefresh = {loading = false},
                isRefreshing = true,
                state = pullToRefreshState) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    items(videos.videos.size) {
                        ListItem({
                                Box(
                                    modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .border(2.dp, Color.Black), contentAlignment = Alignment.BottomEnd
                                ) {
                                AsyncImage(
                                    model = videos.videos[it].image,
                                    contentDescription = "video preview",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            navController.navigate(
                                                VideoScreenObject(
                                                    url = videos.videos[it].videoFiles[0].link
                                                )
                                            )
                                        },
                                    filterQuality = FilterQuality.Low
                                )

                                Text(
                                    text = transformVideoTime(videos.videos[it].duration),
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Start
                                    ),

                                    modifier = Modifier
                                        .wrapContentSize()
                                        .background(color = Color.DarkGray)
                                        .padding(end = 3.dp, bottom = 3.dp)
                                )
                            }
                            Spacer(modifier = Modifier.size(25.dp))
                        })
                    }
                }
            }
        }
}

fun transformVideoTime(duration : Int) : String {
    var d = duration
    val h =  d / 3600
    d %= 3600
    val m  = d / 60
    val s = d % 60
    return "${h}:${m}:${s}"
}
/*@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    MainScreen()
}*/