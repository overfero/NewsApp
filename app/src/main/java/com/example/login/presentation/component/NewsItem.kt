package com.example.login.presentation.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.login.domain.model.News
import com.example.login.presentation.DescriptionNewsItem
import com.example.login.presentation.HeaderNewsItem
import com.example.login.presentation.HeaderTextComponent
import com.example.login.presentation.SmallDetailText
import com.example.login.presentation.TextComponent
import com.example.login.ui.theme.BGNewsItem
import com.example.login.ui.theme.DescColor
import com.example.login.ui.theme.IconColor
import com.example.login.util.Screen
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun NewsItem(
    news: News,
    navHostController: NavHostController,
    onBookmarkChanged: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .padding(8.dp)
        .clip(RoundedCornerShape(22.dp))
        .background(BGNewsItem)
        .clickable { navHostController.navigate(Screen.Detail.route + "/${news.article_id}") }
    ){
        Row(modifier = Modifier.fillMaxWidth()) {
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.image_url)
                    .size(Size.ORIGINAL).build()
            ).state
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDateTime.parse(news.pubDate, formatter)
            val duration = Duration.between(date, LocalDateTime.now())
            val pubDate = if (duration.toHours() < 1) "• ${duration.toMinutes()} minutes ago" else "• ${duration.toHours()} hours ago"

            if (imageState is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp)
                        .width(90.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.ImageNotSupported,
                        contentDescription = null
                    )
                }
            }

            if (imageState is AsyncImagePainter.State.Success){
                Image(
                    painter = imageState.painter,
                    contentDescription = "Gambar",
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(12.dp)
                        .width(90.dp)
                        .clip(RoundedCornerShape(22.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    SmallDetailText(news.category.toString().substring(1, news.category.toString().length - 1).uppercase(), pubDate)
                    IconButton(onClick = { onBookmarkChanged.invoke() }, modifier = Modifier.size(20.dp)) {
                        Icon(
                            imageVector = if (news.bookmark) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            modifier = Modifier.size(20.dp),
                            tint = IconColor
                        )
                    }
                }
                HeaderNewsItem(text = news.title)
                Spacer(modifier = Modifier.height(3.dp))

                DescriptionNewsItem(text = news.description, Modifier.padding(end = 6.dp))
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selected: ImageVector
)

//@Preview(showBackground = true)
//@Composable
//fun PrevNewsItem() {
//    NewsItem(rememberNavController())
//}