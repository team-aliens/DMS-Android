package com.example.dms_android.feature.cafeteria

import android.icu.text.Transliterator.Position
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.color.DormColor
import com.example.design_system.typography.Body4
import com.example.design_system.typography.DormTypography
import com.example.design_system.typography.NotoSansFamily
import com.example.dms_android.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.round

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaMenu(
    pages: List<String>,
    pagerState: PagerState,
) {
    // TODO("급식 로직 추후에 개선 필요 급식 넘기기에서 전 화면과 다음 화면 약간 보여준 다음에 넘기는 애니메이션 구현 못함")

    Box() {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            modifier = Modifier
                .fillMaxHeight(0.72f)
                .fillMaxWidth(0.61f),
            backgroundColor = MaterialTheme.colors.background,
        ) {
            HorizontalPager(
                count = pages.size,
                state = pagerState,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            color = DormColor.DormPrimary,
                            width = 1.dp,
                            shape = RoundedCornerShape(15)
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "\n김범진김범진김범진\n")
                    Text(text = "\n김범진김범진김범진\n")
                    Text(text = "\n김범진김범진김범진\n")
                    Text(text = "\n김범진김범진김범진\n")
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TomorrowCafeteria(
    pages: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    pages.forEachIndexed { index, title ->
        Tab(
            text = { Body4(text = title) },
            selected = pagerState.currentPage == index,
            onClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                }
            },
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun YesterdayCafeteria(
    pages: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    pages.forEachIndexed { index, title ->
        Tab(
            text = { Body4(text = title) },
            selected = pagerState.currentPage == index - 1,
            onClick = {
                coroutineScope.launch {
                    pagerState.scrollToPage(index - 1)
                }
            }
        )
    }
}