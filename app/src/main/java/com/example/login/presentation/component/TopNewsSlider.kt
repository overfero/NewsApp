package com.example.login.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.login.domain.model.News
import com.example.login.presentation.CategoryTag
import com.example.login.presentation.DetailTextSmall
import com.example.login.presentation.HeaderNewsDetail
import com.example.login.ui.theme.IconColor
import com.example.login.util.Screen
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TopNewsSlider(
    news: News,
    navHostController: NavHostController,
    onBookmarkChanged: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)
        .clip(RoundedCornerShape(22.dp))
        .clickable { navHostController.navigate(Screen.Detail.route + "/${news.article_id}") },
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
                    .clip(RoundedCornerShape(22.dp))
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            Color.Black,
                            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.5f)),
                        )
                    },
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(12.dp),
                contentAlignment = Alignment.TopEnd
            ){
                IconButton(
                    onClick = { onBookmarkChanged.invoke() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (news.bookmark) IconColor else Color.White)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = if (news.bookmark) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .padding(1.dp),
                        tint = if (news.bookmark) Color.White else IconColor
                    )
                }
            }

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CategoryTag(text = news.category.toString().substring(1, news.category.toString().length - 1).uppercase())
                    Spacer(modifier = Modifier.width(16.dp))
                    DetailTextSmall(source = news.source_id.uppercase(), time = pubDate)
                }
                Spacer(modifier = Modifier.height(10.dp))

                HeaderNewsDetail(text = news.title, 24)
                Spacer(modifier = Modifier.height(6.dp))

                DetailTextSmall(source = news.country.toString().substring(1, news.country.toString().length - 1).uppercase(), time = news.pubDate)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prev() {
    val bookmark = true
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(12.dp),
        contentAlignment = Alignment.TopEnd
    ){
        IconButton(
            onClick = {  },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (bookmark) IconColor else Color.White)
                .size(40.dp)
        ) {
            Icon(
                imageVector = if (bookmark) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark",
                modifier = Modifier
                    .padding(1.dp),
                tint = if (bookmark) Color.White else IconColor
            )
        }
    }

}