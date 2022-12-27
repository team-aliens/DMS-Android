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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.modifier.dormShadow
import com.example.design_system.typography.Body5
import com.example.design_system.typography.SubTitle1
import com.example.dms_android.R
import com.example.dms_android.feature.home.ScrollEffectPager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaScreen(
    navController: NavController,
) {

    val pagerState = rememberPagerState(3)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DormColor.Gray100),
    ) {
        TopBar()
        Spacer(
            modifier = Modifier
                .height(15.dp)
        )
        ImportantNotice()
        CafeteriaDiary(
            pagerState = pagerState,
            coroutineScope = coroutineScope,
        )
        CafeteriaViewPager()
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
                .height(34.dp)
                .width(97.dp)
                .padding(start = 13.dp, top = 12.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.MainLogo),
        )
    }
}

@Composable
fun ImportantNotice() {

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(horizontal = 20.dp)
            .dormShadow(
                color = DormColor.Gray700,
                offsetY = 1.dp,
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(100),
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp)
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
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = stringResource(id = R.string.icNotice),
        )
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CafeteriaDiary(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {

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

                        },
                    painter = painterResource(id = DormIcon.Backward.drawableId),
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

                        },
                    painter = painterResource(id = R.drawable.ic_meal_next),
                    contentDescription = stringResource(id = R.string.NextButton),
                )
            }
        }
    }
}

@Composable
fun CafeteriaViewPager() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 23.dp),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            painter = painterResource(id = R.drawable.photo_cafeteria_background),
            contentDescription = stringResource(id = R.string.CafeteriaBackground)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScrollEffectPager()
        }
    }
}
