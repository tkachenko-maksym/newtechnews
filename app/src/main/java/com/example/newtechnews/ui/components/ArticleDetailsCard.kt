package com.example.newtechnews.ui.components

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.newtechnews.data.model.Article
import com.example.newtechnews.data.previewArticle
import com.example.newtechnews.utils.formatToDisplayDate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.newtechnews.R

@Composable
fun ArticleDetailsCard(
    article: Article,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = article.articleSource.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "|",
                    style = MaterialTheme.typography.labelLarge
                )
                article.author?.let { author ->
                    Text(
                        text = author,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Text(
                    text = article.publishedAt.formatToDisplayDate(),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            article.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            article.content?.let { content ->
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Continue reading")
                }

                Button(
                    onClick = onBookmarkClick,
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(
                        if (isBookmarked) {
                            ImageVector.vectorResource(R.drawable.ic_bookmark_remove)
                        } else {
                            ImageVector.vectorResource(R.drawable.ic_bookmark_add)
                        },
                        contentDescription = if (isBookmarked) "Remove from bookmarks" else "Add to bookmarks"
                    )
                }
            }

        }
    }
}

//@Preview(
//    showBackground = true, showSystemUi = true
//)
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//)
//@Composable
//fun ArticleDetailsCardPreview() {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            ArticleDetailsCard(article = previewArticle,isBookmarked = true, onBookmarkClick = {} )
//        }
//}