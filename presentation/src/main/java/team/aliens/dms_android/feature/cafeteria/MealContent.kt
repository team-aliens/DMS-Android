package team.aliens.dms_android.feature.cafeteria

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.typography.Body4
import team.aliens.dms_android.viewmodel.home.MealViewModel
import kotlin.math.absoluteValue

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScrollEffectPager(
    mealViewModel: MealViewModel,
) {

    val pagerState = rememberPagerState()

    val state = mealViewModel.state.collectAsState().value

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 40.dp,
            )
            .background(Color.White),
        contentPadding = PaddingValues(
            horizontal = 64.dp,
        ),
        count = 3,
    ) { pageIndex ->
        Card(
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.dp),
                )
                .graphicsLayer {

                    val pageOffset = calculateCurrentOffsetForPage(pageIndex).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha =
                        lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        )
                }
                .clip(RoundedCornerShape(15))
                .border(
                    width = 1.dp,
                    color = DormColor.DormPrimary,
                    shape = RoundedCornerShape(15),
                ),
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                when (pageIndex) {
                    0 -> {
                        MenuListLayout(
                            state.mealList.breakfast,
                            DormIcon.Breakfast,
                        )
                    }
                    1 -> {
                        MenuListLayout(
                            state.mealList.lunch,
                            DormIcon.Launch,
                        )
                    }
                    2 -> {
                        MenuListLayout(
                            state.mealList.dinner,
                            DormIcon.Dinner,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuListLayout(
    menus: List<String>,
    dormIcon: DormIcon,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(25.dp))
            Image(
                painter = painterResource(
                    id = dormIcon.drawableId,
                ),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(25.dp))
        }
        items(menus) { menu ->
            Spacer(
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
            )
            Body4(
                text = menu
            )
        }

        items(1) { menu ->
            Spacer(modifier = Modifier.height(30.dp))
            if (menus.size > 1) {
                Body4(
                    text = menus.last(),
                    color = DormColor.Gray500
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}