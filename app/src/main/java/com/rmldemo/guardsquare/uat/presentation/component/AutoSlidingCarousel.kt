package com.rmldemo.guardsquare.uat.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmldemo.guardsquare.uat.ui.theme.inverseOnSurfaceLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 3000,
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val pagerState: PagerState = rememberPagerState(pageCount = {
        itemsCount
    })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    val scope = rememberCoroutineScope()
    with(pagerState) {
        LaunchedEffect(key1 = currentPage) {
            launch {
                delay(autoSlideDuration)
                scope.launch {
                    animateScrollToPage(
                        page = (currentPage + 1).mod(pageCount)
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(state = pagerState) { page ->
            itemContent(page)
        }
        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = inverseOnSurfaceLight.copy(alpha = 0.8f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}