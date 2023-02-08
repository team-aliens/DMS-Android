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

    val pagerState = rememberPagerState(initialPage = 1)

    val state = mealViewModel.state.collectAsState().value

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 40.dp)
            .background(Color.Transparent),
        contentPadding = PaddingValues(horizontal = 64.dp),
        count = 3,
    ) { pageIndex ->
        Card(
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(15),
                )
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(pageIndex).absoluteValue
                    lerp(start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha =
                        lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
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
                when (pagerState.currentPage) {
                    0 -> {
                        MenuListLayout(state.mealList.breakfast, 1)
                    }
                    1 -> {
                        MenuListLayout(state.mealList.lunch, 2)
                    }
                    2 -> {
                        MenuListLayout(state.mealList.dinner, 3)
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuListLayout(
    menus: List<String>,
    a: Int,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Spacer(modifier = Modifier.height(9.dp))
            var b: DormIcon = DormIcon.Launch
            when (a) {
                1 -> b = DormIcon.Breakfast
                2 -> b = DormIcon.Launch
                3 -> b = DormIcon.Dinner
            }
            Image(painter = painterResource(id = b.drawableId), contentDescription = "")
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(menus) { menu ->
            Body4(text = menu)
        }
    }
}