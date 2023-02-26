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
            .background(Color.Transparent),
        contentPadding = PaddingValues(
            horizontal = 64.dp,
        ),
        count = 3,
    ) { pageIndex ->
        Card(
            modifier = Modifier
                .fillMaxSize()
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

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f),
                    )
                }
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.dp),
                )
                .clip(
                    RoundedCornerShape(20.dp),
                )
                .border(
                    width = 1.dp,
                    color = DormColor.DormPrimary,
                    shape = RoundedCornerShape(20.dp),
                ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                when (pageIndex) {
                    0 -> {
                        MenuListLayout(
                            icon = DormIcon.Breakfast,
                            menus = state.mealList.breakfast,
                        )
                    }
                    1 -> {
                        MenuListLayout(
                            icon = DormIcon.Lunch,
                            menus = state.mealList.lunch,
                        )
                    }
                    2 -> {
                        MenuListLayout(
                            icon = DormIcon.Dinner,
                            menus = state.mealList.dinner,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuListLayout(
    icon: DormIcon,
    menus: Pair<List<String>, String>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        item {
            Image(
                painter = painterResource(
                    id = icon.drawableId,
                ),
                contentDescription = null,
            )
        }

        items(
            menus.first,
        ) { menu ->
            Body4(
                text = menu,
                color = DormColor.Gray600,
            )
        }

        item {
            Body4(
                text = menus.second,
                color = DormColor.Gray500,
            )
        }
    }
}