package com.team2.todo.common_ui_components

import androidx.compose.foundation.Image
import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ImageLoader(bitmapList: List<Bitmap?>, onClick: () -> Unit = {}) {
    var currentImageIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Column() {

        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()

        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(bitmapList) { index, bitmap ->
                    Card(colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                        modifier = Modifier
                            .clickable {
                                onClick()
                                if (index != currentImageIndex) {
                                    coroutineScope.launch {
                                        currentImageIndex = index
                                    }
                                }
                            }
                            .fillParentMaxHeight()
                            .fillParentMaxWidth(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp,
                        )

                    ) {

                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.5f)
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                contentScale = ContentScale.Fit,
                                alignment = Alignment.Center

                            )
                        }
                    }
                }

            }

        }
    }
}
