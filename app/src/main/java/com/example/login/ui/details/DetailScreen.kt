package com.example.login.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.login.presentation.CategoryTag
import com.example.login.presentation.DescriptionNewsDetail
import com.example.login.presentation.DetailTextSmall
import com.example.login.presentation.HeaderNewsDetail
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailScreen() {
    val viewModel: DetailViewModel = hiltViewModel()
    val state = viewModel.state
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                state.news?.let {news ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                        contentAlignment = Alignment.BottomStart
                    ){
                        val imageState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(news.image_url)
                                .size(Size.ORIGINAL)
                                .build()
                        ).state
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        val date = LocalDateTime.parse(news.pubDate, formatter)
                        val duration = Duration.between(date, LocalDateTime.now())
                        val pubDate = if (duration.toHours() < 1) "• ${duration.toMinutes()} minutes ago" else "• ${duration.toHours()} hours ago"

                        if (imageState is AsyncImagePainter.State.Success){
                            Image(
                                painter = imageState.painter,
                                contentDescription = "Gambar",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(350.dp)
                                    .drawWithContent {
                                        drawContent()
                                        drawRect(
                                            Color.Black,
                                            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f))
                                        )
                                    },
                                contentScale = ContentScale.Crop
                            )

                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CategoryTag(text = news.category.toString().substring(1, news.category.toString().length - 1).uppercase())
                                    Spacer(modifier = Modifier.width(16.dp))
                                    DetailTextSmall(source = news.source_id.uppercase(), time = pubDate)
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                HeaderNewsDetail(text = news.title, 20)
                                Spacer(modifier = Modifier.height(4.dp))

                                DetailTextSmall(source = news.country.toString().substring(1, news.country.toString().length - 1).uppercase(), time = news.pubDate)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    DescriptionNewsDetail(text = news.content, Modifier.padding(start = 12.dp, end = 12.dp))
                }
            }
        }
    }
}