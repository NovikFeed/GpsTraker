package com.example.gps.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.math.BigDecimal
import kotlin.math.absoluteValue

@Composable
fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { page ->
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size = (10 * offsetPercentage).dp

                    Color.Gray.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    Color.Gray.copy(alpha = 0.1f) to 8.dp
                }
            Box(modifier = Modifier.padding(
                horizontal =  24.dp - size / 2,
                vertical = size / 4
            )
                .clip(RoundedCornerShape(2.dp))
                .background(color)
                .width(size)
                .height(size / 2))
        }
    }
}