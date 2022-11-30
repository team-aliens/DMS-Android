package com.example.dms_android.feature.cafeteria

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.typography.Body5
import com.example.design_system.typography.SubTitle1
import com.example.dms_android.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaScreen() {

    val pages: List<String> = listOf(
        stringResource(id = R.string.AM1),
        stringResource(id = R.string.AL1),
        stringResource(id = R.string.AD1),
        stringResource(id = R.string.AM2),
        stringResource(id = R.string.AL2),
        stringResource(id = R.string.AD2),
        stringResource(id = R.string.AM3),
        stringResource(id = R.string.AL3),
        stringResource(id = R.string.AD3),
        stringResource(id = R.string.AM4),
        stringResource(id = R.string.AL4),
        stringResource(id = R.string.AD4),
        stringResource(id = R.string.AM5),
        stringResource(id = R.string.AL5),
        stringResource(id = R.string.AD5),
        stringResource(id = R.string.AM6),
        stringResource(id = R.string.AL6),
        stringResource(id = R.string.AD6),
        stringResource(id = R.string.AM7),
        stringResource(id = R.string.AL7),
        stringResource(id = R.string.AD7),
        stringResource(id = R.string.TM),
        stringResource(id = R.string.TL),
        stringResource(id = R.string.TD),
        stringResource(id = R.string.LM1),
        stringResource(id = R.string.LL1),
        stringResource(id = R.string.LD1),
        stringResource(id = R.string.LM2),
        stringResource(id = R.string.LL2),
        stringResource(id = R.string.LD2),
        stringResource(id = R.string.LM3),
        stringResource(id = R.string.LL3),
        stringResource(id = R.string.LD3),
        stringResource(id = R.string.LM4),
        stringResource(id = R.string.LL4),
        stringResource(id = R.string.LD4),
        stringResource(id = R.string.LM5),
        stringResource(id = R.string.LL5),
        stringResource(id = R.string.LD5),
        stringResource(id = R.string.LM6),
        stringResource(id = R.string.LL6),
        stringResource(id = R.string.LD6),
        stringResource(id = R.string.LM7),
        stringResource(id = R.string.LL7),
        stringResource(id = R.string.LD7),
    )

    val pagerState = rememberPagerState(23)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray100),
    ) {
        TopBar()
        ImportantNotice()
        CafeteriaDiary(
            pages = pages,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
        )
        CafeteriaViewPager(
            pages = pages,
            pagerState = pagerState,
        )
    }
}

@Composable
fun TopBar(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart,
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .padding(start = 13.dp, top = 10.dp),
            painter = painterResource(id = R.drawable.temporarylogo),
            contentDescription = stringResource(id = R.string.MainLogo),
        )
    }
}

@Composable
fun ImportantNotice() {

    val padding = 20.dp
    val density = LocalDensity.current

    Surface(
        shape = RectangleShape,
        elevation = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .drawWithContent {
                val paddingPx = with(density) { padding.toPx() }
                clipRect(
                    left = -paddingPx,
                    top = 0f,
                    right = size.width + paddingPx,
                    bottom = size.height + paddingPx
                ) {
                    this@drawWithContent.drawContent()
                }
            }
            .padding(start = 28.dp, end = 28.dp, top = 40.dp)
            .height(45.dp)
            .background(DormColor.Gray100),
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.ic_notice),
                    contentDescription = stringResource(id = R.string.icNotice),
                )
                Spacer(
                    modifier = Modifier
                        .width(13.dp)
                )
                Body5(
                    text = "새로운 공지사항이 있습니다."
                )
            }
            Image(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(33.dp),
                painter = painterResource(id = R.drawable.ic_noticedetail),
                contentDescription = stringResource(id = R.string.icNotice),
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaDiary(
    pages: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    val yesterdayField: @Composable () -> Unit = {
        YesterdayCafeteria(
            pages = pages,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
        )
    }

    val tomorrowField: @Composable () -> Unit = {
        TomorrowCafeteria(
            pages = pages,
            pagerState = pagerState,
            coroutineScope = coroutineScope,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.055f)
            )
            SubTitle1(text = stringResource(id = R.string.TodayCafeteria))
            Row(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 12.dp)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        },
                    painter = painterResource(id = R.drawable.yesterday),
                    contentDescription = stringResource(id = R.string.backButton)
                )
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = DormColor.Gray500,
                            shape = RoundedCornerShape(25),
                        )
                        .width(130.dp)
                        .height(35.dp),
                ) {
                }
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 12.dp, top = 5.dp)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        },
                    painter = painterResource(id = R.drawable.tomorrow),
                    contentDescription = stringResource(id = R.string.NextButton),
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaViewPager(
    pages: List<String>,
    pagerState: PagerState,
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            painter = painterResource(id = R.drawable.cafeteria_background),
            contentDescription = stringResource(id = R.string.CafeteriaBackground)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CafeteriaMenu(
                pages = pages,
                pagerState = pagerState,
            )
        }
    }
}

@Preview
@Composable
fun CafeteriaPreView() {
    CafeteriaScreen()
}
