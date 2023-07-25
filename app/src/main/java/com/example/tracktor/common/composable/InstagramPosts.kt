package com.example.tracktor.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.tracktor.common.functions.convertToEST12HourTime
import com.example.tracktor.data.model.InstagramPost
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun InstagramPostDay(date: String, posts: List<InstagramPost>) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Divider()
        Text(
            text = date,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            color = Color.Black,
            modifier = Modifier.padding(15.dp)
        )
        Divider()
        posts.forEach { post ->
            Row { InstagramPosts(post) }
        }


    }
}

@Composable
fun InstagramPosts(post: InstagramPost, modifier: Modifier = Modifier) {
    println(post.image_url)


    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(0.87f), horizontalArrangement = Arrangement.End) {
            Text(
                text = convertToEST12HourTime(post.time_stamp),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(end = 10.dp)

            )
        }

        SubcomposeAsyncImage(
            model = post.image_url,
            contentDescription = null,
            loading = { LoadingSpinner() }
        )

    }
}

@Composable
@Preview
fun InstagramPostsPreview() {
    TracktorTheme {
        Column {
            InstagramPostDay(
                "July 20, 2023", listOf(
                    InstagramPost(
                        "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                        "7:35 AM"
                    ),
                    InstagramPost(
                        "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                        "7:25 AM"
                    )
                )
            )
            InstagramPostDay(
                "July 18, 2023", listOf(
                    InstagramPost(
                        "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                        "7:35 AM"
                    ),
                    InstagramPost(
                        "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                        "7:25 AM"
                    )
                )
            )
        }

    }
}

